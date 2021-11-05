package dev.inmo.micro_utils.fsm.common

import dev.inmo.micro_utils.coroutines.launchSafelyWithoutExceptions
import dev.inmo.micro_utils.coroutines.subscribeSafelyWithoutExceptions
import kotlinx.coroutines.*

/**
 * Default [StatesMachine] may [startChain] and use inside logic for handling [State]s. By default you may use
 * [DefaultStatesMachine] or build it with [dev.inmo.micro_utils.fsm.common.dsl.buildFSM]. Implementers MUST NOT start
 * handling until [start] method will be called
 */
interface StatesMachine<T : State> : StatesHandler<T, T> {
    suspend fun launchStateHandling(
        state: T,
        handlers: List<CustomizableHandlerHolder<in T, T>>
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
            handlers: List<CustomizableHandlerHolder<in T, T>>
        ) = DefaultStatesMachine(statesManager, handlers)
    }
}

/**
 * Default realization of [StatesMachine]. It uses [statesManager] for incapsulation of [State]s storing and contexts
 * resolving, and uses [launchStateHandling] for [State] handling
 */
class DefaultStatesMachine <T: State>(
    private val statesManager: StatesManager<T>,
    private val handlers: List<CustomizableHandlerHolder<in T, T>>
) : StatesMachine<T> {
    /**
     * Will call [launchStateHandling] for state handling
     */
    override suspend fun StatesMachine<in T>.handleState(state: T): T? = launchStateHandling(state, handlers)

    /**
     * Launch handling of states. On [statesManager] [StatesManager.onStartChain],
     * [statesManager] [StatesManager.onChainStateUpdated] will be called lambda with performing of state. If
     * [launchStateHandling] will returns some [State] then [statesManager] [StatesManager.update] will be used, otherwise
     * [StatesManager.endChain].
     */
    override fun start(scope: CoroutineScope): Job = scope.launchSafelyWithoutExceptions {
        val statePerformer: suspend (T) -> Unit = { state: T ->
            val newState = launchStateHandling(state, handlers)
            if (newState != null) {
                statesManager.update(state, newState)
            } else {
                statesManager.endChain(state)
            }
        }
        statesManager.onStartChain.subscribeSafelyWithoutExceptions(this) {
            launch { statePerformer(it) }
        }
        statesManager.onChainStateUpdated.subscribeSafelyWithoutExceptions(this) {
            launch { statePerformer(it.second) }
        }

        statesManager.getActiveStates().forEach {
            launch { statePerformer(it) }
        }
    }

    /**
     * Just calls [StatesManager.startChain] of [statesManager]
     */
    override suspend fun startChain(state: T) {
        statesManager.startChain(state)
    }
}
