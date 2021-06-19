package dev.inmo.micro_utils.fsm.common.dsl

import dev.inmo.micro_utils.fsm.common.*
import kotlin.reflect.KClass

class FSMBuilder(
    var statesManager: StatesManager = InMemoryStatesManager()
) {
    private var states = mutableListOf<StateHandlerHolder<*>>()

    fun <I : State> add(kClass: KClass<I>, handler: StatesHandler<I>) {
        states.add(StateHandlerHolder(kClass, false, handler))
    }

    fun <I : State> addStrict(kClass: KClass<I>, handler: StatesHandler<I>) {
        states.add(StateHandlerHolder(kClass, true, handler))
    }

    fun build() = StatesMachine(
        statesManager,
        states.toList()
    )
}

inline fun <reified I : State> FSMBuilder.onStateOrSubstate(handler: StatesHandler<I>) {
    add(I::class, handler)
}

inline fun <reified I : State> FSMBuilder.strictlyOn(handler: StatesHandler<I>) {
    addStrict(I::class, handler)
}

fun buildFSM(
    block: FSMBuilder.() -> Unit
): StatesMachine = FSMBuilder().apply(block).build()
