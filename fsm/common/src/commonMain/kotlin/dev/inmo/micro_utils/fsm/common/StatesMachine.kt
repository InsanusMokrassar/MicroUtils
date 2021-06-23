package dev.inmo.micro_utils.fsm.common

import dev.inmo.micro_utils.coroutines.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.asFlow

private suspend fun <I : State> StatesMachine.launchStateHandling(
    state: State,
    handlers: List<StateHandlerHolder<out I>>
): State? {
    return handlers.firstOrNull { it.checkHandleable(state) } ?.run {
        handleState(state)
    }
}

class StatesMachine (
    private val statesManager: StatesManager,
    private val handlers: List<StateHandlerHolder<*>>
) : StatesHandler<State> {
    override suspend fun StatesMachine.handleState(state: State): State? = launchStateHandling(state, handlers)

    fun start(scope: CoroutineScope): Job = scope.launchSafelyWithoutExceptions {
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

    suspend fun startChain(state: State) {
        statesManager.startChain(state)
    }
}
