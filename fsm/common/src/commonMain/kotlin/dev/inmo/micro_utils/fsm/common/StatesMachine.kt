package dev.inmo.micro_utils.fsm.common

import dev.inmo.kslog.common.TagLogger
import dev.inmo.kslog.common.e
import dev.inmo.micro_utils.common.Optional
import dev.inmo.micro_utils.coroutines.*
import dev.inmo.micro_utils.fsm.common.utils.StateHandlingErrorHandler
import dev.inmo.micro_utils.fsm.common.utils.defaultStateHandlingErrorHandler
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.asFlow
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
        handlers: List<CheckableHandlerHolder<in T, T>>,
        onStateHandlingErrorHandler: StateHandlingErrorHandler<T>
    ): T? {
        return runCatchingSafely {
            handlers.firstOrNull { it.checkHandleable(state) } ?.run {
                handleState(state)
            }
        }.getOrElse {
            onStateHandlingErrorHandler(state, it)
        }
    }
    suspend fun launchStateHandling(
        state: T,
        handlers: List<CheckableHandlerHolder<in T, T>>
    ): T? {
        return launchStateHandling(state, handlers, defaultStateHandlingErrorHandler())
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
            handlers: List<CheckableHandlerHolder<in T, T>>,
            onStateHandlingErrorHandler: StateHandlingErrorHandler<T> = defaultStateHandlingErrorHandler()
        ) = DefaultStatesMachine(statesManager, handlers, onStateHandlingErrorHandler)
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
    protected val onStateHandlingErrorHandler: StateHandlingErrorHandler<T> = defaultStateHandlingErrorHandler()
) : StatesMachine<T> {
    protected val logger = TagLogger(this::class.simpleName!!)
    /**
     * Will call [launchStateHandling] for state handling
     */
    override suspend fun StatesMachine<in T>.handleState(state: T): T? = launchStateHandling(state, handlers)

    override suspend fun launchStateHandling(state: T, handlers: List<CheckableHandlerHolder<in T, T>>): T? {
        return launchStateHandling(state, handlers, onStateHandlingErrorHandler)
    }

    /**
     * This
     */
    protected val statesJobs = mutableMapOf<T, Job>()
    protected val statesJobsMutex = Mutex()

    protected open suspend fun performUpdate(state: T) {
        val newState = launchStateHandling(state, handlers)
        if (newState == null) {
            statesManager.endChain(state)
        } else {
            statesManager.update(state, newState)
        }
    }

    open suspend fun performStateUpdate(previousState: Optional<T>, actualState: T, scope: CoroutineScope) {
        statesJobsMutex.withLock {
            statesJobs[actualState] ?.cancel()
            statesJobs[actualState] = scope.launch {
                runCatching {
                    performUpdate(actualState)
                }.onFailure {
                    logger.e(it) {
                        "Unable to perform update of state from $actualState"
                    }
                }.getOrThrow()
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
    override fun start(scope: CoroutineScope): Job {
        val supervisorScope = scope.LinkedSupervisorScope()
        supervisorScope.launchLoggingDropExceptions {
            (statesManager.getActiveStates().asFlow() + statesManager.onStartChain).subscribeSafelyWithoutExceptions(
                supervisorScope
            ) {
                supervisorScope.launch { performStateUpdate(Optional.absent(), it, supervisorScope) }
            }
            statesManager.onChainStateUpdated.subscribeSafelyWithoutExceptions(supervisorScope) {
                supervisorScope.launch { performStateUpdate(Optional.presented(it.first), it.second, supervisorScope) }
            }
            statesManager.onEndChain.subscribeSafelyWithoutExceptions(supervisorScope) { removedState ->
                supervisorScope.launch {
                    statesJobsMutex.withLock {
                        val stateInMap = statesJobs.keys.firstOrNull { stateInMap -> stateInMap == removedState }
                        if (stateInMap === removedState) {
                            statesJobs[stateInMap]?.cancel()
                        }
                    }
                }
            }
        }

        return supervisorScope.coroutineContext.job
    }

    /**
     * Just calls [StatesManager.startChain] of [statesManager]
     */
    override suspend fun startChain(state: T) {
        statesManager.startChain(state)
    }
}
