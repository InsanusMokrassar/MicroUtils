package dev.inmo.micro_utils.fsm.common.dsl

import dev.inmo.micro_utils.fsm.common.*
import dev.inmo.micro_utils.fsm.common.managers.*
import kotlin.reflect.KClass

class FSMBuilder<T : State>(
    var statesManager: StatesManager<T> = DefaultStatesManager(InMemoryDefaultStatesManagerRepo()),
    var defaultStateHandler: StatesHandler<T, T>? = StatesHandler { null }
) {
    private var states = mutableListOf<CustomizableHandlerHolder<T, T>>()

    fun <I : T> add(kClass: KClass<I>, handler: StatesHandler<I, T>) {
        states.add(StateHandlerHolder(kClass, false, handler))
    }

    fun <I : T> add(filter: suspend (state: State) -> Boolean, handler: StatesHandler<I, T>) {
        states.add(handler.holder(filter))
    }

    fun <I : T> addStrict(kClass: KClass<I>, handler: StatesHandler<I, T>) {
        states.add(StateHandlerHolder(kClass, true, handler))
    }

    inline fun <reified I : T> onStateOrSubstate(handler: StatesHandler<I, T>) {
        add(I::class, handler)
    }

    inline fun <reified I : T> strictlyOn(handler: StatesHandler<I, T>) {
        addStrict(I::class, handler)
    }

    inline fun <reified I : T> doWhen(
        noinline filter: suspend (state: State) -> Boolean,
        handler: StatesHandler<I, T>
    ) {
        add(filter, handler)
    }

    fun build() = StatesMachine(
        statesManager,
        states.toList().let { list ->
            defaultStateHandler ?.let { list + it.holder { true } } ?: list
        }
    )
}

fun <T : State> buildFSM(
    block: FSMBuilder<T>.() -> Unit
): StatesMachine<T> = FSMBuilder<T>().apply(block).build()
