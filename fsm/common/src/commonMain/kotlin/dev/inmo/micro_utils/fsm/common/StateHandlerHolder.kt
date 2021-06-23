package dev.inmo.micro_utils.fsm.common

import kotlin.reflect.KClass

class StateHandlerHolder<I : State>(
    private val inputKlass: KClass<I>,
    private val strict: Boolean = false,
    private val delegateTo: StatesHandler<I>
) : StatesHandler<State> {
    fun checkHandleable(state: State) = state::class == inputKlass || (!strict && inputKlass.isInstance(state))

    override suspend fun StatesMachine.handleState(state: State): State? {
        return delegateTo.run { handleState(state as I) }
    }
}
