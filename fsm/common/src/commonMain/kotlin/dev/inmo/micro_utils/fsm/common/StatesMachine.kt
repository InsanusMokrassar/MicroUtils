package dev.inmo.micro_utils.fsm.common

import dev.inmo.micro_utils.common.Optional
import dev.inmo.micro_utils.common.onPresented
import dev.inmo.micro_utils.coroutines.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Default [StatesMachine] may [startChain] and use inside logic for handling [State]s. By default you may use
 * [DefaultStatesMachine] or build it with [dev.inmo.micro_utils.fsm.common.dsl.buildFSM]. Implementers MUST NOT start
 * handling until [start] method will be called
 */
interface StatesMachine<T : State> : StatesHandler<T, T> {
    suspend fun launchStateHandling(
        state: T,
        handlers: List<CheckableHandlerHolder<in T, T>>
    ): T? {
        return handlers.firstOrNull { it.checkHandleable(state) } ?.run {
            handleState(state)
        }
    }

    /**
     * Starts handling of [State]s
     */
    fun start(scope: CoroutineScope): Job

    /**
     * Start chain of [State]s witn [state]
     */
    suspend fun startChain(state: T)

    companion object {
        /**
         * Creates [DefaultStatesMachine]
         */
        operator fun <T: State> invoke(
            statesManager: StatesManager<T>,
            handlers: List<CheckableHandlerHolder<in T, T>>
        ) = DefaultStatesMachine(statesManager, handlers)
    }
}

/**
 * Default realization of [StatesMachine]. It uses [statesManager] for incapsulation of [State]s storing and contexts
 * resolving, and uses [launchStateHandling] for [State] handling.
 *
 * This class suppose to be extended in case you wish some custom behaviour inside of [launchStateHandling], for example
 */
open class DefaultStatesMachine <T: State>(
    protected val statesManager: StatesManager<T>,
    protected val handlers: List<CheckableHandlerHolder<in T, T>>,
) : StatesMachine<T> {
    /**
     * Will call [launchStateHandling] for state handling
     */
    override suspend fun StatesMachine<in T>.handleState(state: T): T? = launchStateHandling(state, handlers)

    /**
     * This
     */
    protected val statesJobs = mutableMapOf<T, Job>()
    protected val statesJobsMutex = Mutex()

    protected open suspend fun performUpdate(state: T) {
        val newState = launchStateHandling(state, handlers)
        if (newState != null) {
            statesManager.update(state, newState)
        } else {
            statesManager.endChain(state)
        }
    }

    open suspend fun performStateUpdate(previousState: Optional<T>, actualState: T, scope: CoroutineScope) {
        statesJobsMutex.withLock {
            statesJobs[actualState] ?.cancel()
            statesJobs[actualState] = scope.launch {
                performUpdate(actualState)
            }.also { job ->
                job.invokeOnCompletion { _ ->
                    scope.launch {
                        statesJobsMutex.withLock {
                            if (statesJobs[actualState] == job) {
                                statesJobs.remove(actualState)
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Launch handling of states. On [statesManager] [StatesManager.onStartChain],
     * [statesManager] [StatesManager.onChainStateUpdated] will be called lambda with performing of state. If
     * [launchStateHandling] will returns some [State] then [statesManager] [StatesManager.update] will be used, otherwise
     * [StatesManager.endChain].
     */
    override fun start(scope: CoroutineScope): Job = scope.launchSafelyWithoutExceptions {
        statesManager.onStartChain.subscribeSafelyWithoutExceptions(this) {
            launch { performStateUpdate(Optional.absent(), it, scope.LinkedSupervisorScope()) }
        }
        statesManager.onChainStateUpdated.subscribeSafelyWithoutExceptions(this) {
            launch { performStateUpdate(Optional.presented(it.first), it.second, scope.LinkedSupervisorScope()) }
        }

        statesManager.getActiveStates().forEach {
            launch { performStateUpdate(Optional.absent(), it, scope.LinkedSupervisorScope()) }
        }
    }

    /**
     * Just calls [StatesManager.startChain] of [statesManager]
     */
    override suspend fun startChain(state: T) {
        statesManager.startChain(state)
    }
}
