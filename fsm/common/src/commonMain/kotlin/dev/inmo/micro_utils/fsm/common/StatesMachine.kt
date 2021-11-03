package dev.inmo.micro_utils.fsm.common

import dev.inmo.micro_utils.coroutines.*
import kotlinx.coroutines.*

private suspend fun <I : State> StatesMachine.launchStateHandling(
    state: State,
    handlers: List<StatesHandlerHolder<out I>>
): State? {
    return handlers.firstOrNull { it.checkHandleable(state) } ?.run {
        handleState(state)
    }
}

/**
 * Default [StatesMachine] may [startChain] and use inside logic for handling [State]s. By default you may use
 * [DefaultStatesMachine] or build it with [dev.inmo.micro_utils.fsm.common.dsl.buildFSM]. Implementers MUST NOT start
 * handling until [start] method will be called
 */
interface StatesMachine : StatesHandler<State> {
    /**
     * Starts handling of [State]s
     */
    fun start(scope: CoroutineScope): Job

    /**
     * Start chain of [State]s witn [state]
     */
    suspend fun startChain(state: State)

    companion object {
        /**
         * Creates [DefaultStatesMachine]
         */
        operator fun invoke(
            statesManager: StatesManager,
            handlers: List<StatesHandlerHolder<*>>
        ) = DefaultStatesMachine(statesManager, handlers)
    }
}

/**
 * Default realization of [StatesMachine]. It uses [statesManager] for incapsulation of [State]s storing and contexts
 * resolving, and uses [launchStateHandling] for [State] handling
 */
class DefaultStatesMachine (
    private val statesManager: StatesManager,
    private val handlers: List<StatesHandlerHolder<*>>
) : StatesMachine {
    /**
     * Will call [launchStateHandling] for state handling
     */
    override suspend fun StatesMachine.handleState(state: State): State? = launchStateHandling(state, handlers)

    /**
     * Launch handling of states. On [statesManager] [StatesManager.onStartChain],
     * [statesManager] [StatesManager.onChainStateUpdated] will be called lambda with performing of state. If
     * [launchStateHandling] will returns some [State] then [statesManager] [StatesManager.update] will be used, otherwise
     * [StatesManager.endChain].
     */
    override fun start(scope: CoroutineScope): Job = scope.launchSafelyWithoutExceptions {
        val statePerformer: suspend (State) -> Unit = { state: State ->
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
    override suspend fun startChain(state: State) {
        statesManager.startChain(state)
    }
}
