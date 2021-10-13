package dev.inmo.micro_utils.fsm.common

/**
 * Default realization of states handler
 */
fun interface StatesHandler<I : State> {
    /**
     *
     */
    suspend fun StatesMachine.handleState(state: I): State?
}
