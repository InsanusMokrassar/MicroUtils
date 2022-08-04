package dev.inmo.micro_utils.fsm.common

import kotlin.reflect.KClass

/**
 * Define checkable holder which can be used to precheck that this handler may handle incoming [State]
 */
interface CheckableHandlerHolder<I : State, O : State> : StatesHandler<I, O> {
    suspend fun checkHandleable(state: O): Boolean
}

/**
 * Default realization of [StatesHandler]. It will incapsulate checking of [State] type in [checkHandleable] and class
 * casting in [handleState]
 */
class CustomizableHandlerHolder<I : O, O : State>(
    private val delegateTo: StatesHandler<I, O>,
    private val filter: suspend (state: O) -> Boolean
) : CheckableHandlerHolder<I, O> {
    /**
     * Checks that [state] can be handled by [delegateTo]. Under the hood it will check exact equality of [state]
     * [KClass] and use [KClass.isInstance] of [inputKlass] if [strict] == false
     */
    override suspend fun checkHandleable(state: O) = filter(state)

    /**
     * Calls [delegateTo] method [StatesHandler.handleState] with [state] casted to [I]. Use [checkHandleable]
     * to be sure that this [StatesHandlerHolder] will be able to handle [state]
     */
    override suspend fun StatesMachine<in O>.handleState(state: I): O? {
        return delegateTo.run { handleState(state) }
    }
}

fun <I : O, O : State> CheckableHandlerHolder(
    inputKlass: KClass<I>,
    strict: Boolean = false,
    delegateTo: StatesHandler<I, O>
) = CustomizableHandlerHolder(
    StatesHandler<O, O> {
        delegateTo.run { handleState(it as I) }
    },
    if (strict) {
        { it::class == inputKlass }
    } else {
        { inputKlass.isInstance(it) }
    }
)

inline fun <reified I : O, O : State> CheckableHandlerHolder(
    strict: Boolean = false,
    delegateTo: StatesHandler<I, O>
) = CheckableHandlerHolder(I::class, strict, delegateTo)

inline fun <reified I : O, O: State> StatesHandler<I, O>.holder(
    strict: Boolean = true
) = CheckableHandlerHolder<I, O>(
    I::class,
    strict,
    this
)

inline fun <I : O, O: State> StatesHandler<I, O>.holder(
    noinline filter: suspend (state: State) -> Boolean
) = CustomizableHandlerHolder<O, O>(
    { this@holder.run { handleState(it as I) } },
    filter
)
