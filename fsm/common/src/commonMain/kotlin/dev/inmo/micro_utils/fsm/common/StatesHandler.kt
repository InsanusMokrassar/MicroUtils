package dev.inmo.micro_utils.fsm.common

fun interface StatesHandler<I : State> {
    suspend fun StatesMachine.handleState(state: I): State?
}
