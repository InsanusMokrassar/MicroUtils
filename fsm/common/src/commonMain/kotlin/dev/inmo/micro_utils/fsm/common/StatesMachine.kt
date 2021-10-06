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

interface StatesMachine : StatesHandler<State> {
    fun start(scope: CoroutineScope): Job
    suspend fun startChain(state: State)

    companion object {
        /**
         * Creates [DefaultStatesMachine]
         */
        operator fun invoke(
            statesManager: StatesManager,
            handlers: List<StateHandlerHolder<*>>
        ) = DefaultStatesMachine(statesManager, handlers)
    }
}

class DefaultStatesMachine (
    private val statesManager: StatesManager,
    private val handlers: List<StateHandlerHolder<*>>
) : StatesMachine {
    override suspend fun StatesMachine.handleState(state: State): State? = launchStateHandling(state, handlers)

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

    override suspend fun startChain(state: State) {
        statesManager.startChain(state)
    }
}
