package dev.inmo.micro_utils.fsm.common

import kotlin.reflect.KClass

/**
 * Default realization of [StatesHandler]. It will incapsulate checking of [State] type in [checkHandleable] and class
 * casting in [handleState]
 */
class StateHandlerHolder<I : State>(
    private val inputKlass: KClass<I>,
    private val strict: Boolean = false,
    private val delegateTo: StatesHandler<I>
) : StatesHandler<State> {
    /**
     * Checks that [state] can be handled by [delegateTo]. Under the hood it will check exact equality of [state]
     * [KClass] and use [KClass.isInstance] of [inputKlass] if [strict] == false
     */
    fun checkHandleable(state: State) = state::class == inputKlass || (!strict && inputKlass.isInstance(state))

    /**
     * Calls [delegateTo] method [StatesHandler.handleState] with [state] casted to [I]. Use [checkHandleable]
     * to be sure that this [StateHandlerHolder] will be able to handle [state]
     */
    override suspend fun StatesMachine.handleState(state: State): State? {
        return delegateTo.run { handleState(state as I) }
    }
}
