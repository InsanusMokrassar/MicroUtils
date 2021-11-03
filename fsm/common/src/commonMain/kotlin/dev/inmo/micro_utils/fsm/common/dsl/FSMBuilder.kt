package dev.inmo.micro_utils.fsm.common.dsl

import dev.inmo.micro_utils.fsm.common.*
import dev.inmo.micro_utils.fsm.common.managers.*
import kotlin.reflect.KClass

class FSMBuilder(
    var statesManager: StatesManager = DefaultStatesManager(InMemoryDefaultStatesManagerRepo()),
    var defaultStateHandler: StatesHandler<State>? = StatesHandler { null }
) {
    private var states = mutableListOf<StatesHandlerHolder<*>>()

    fun <I : State> add(kClass: KClass<I>, handler: StatesHandler<I>) {
        states.add(StatesHandlerHolder(kClass, false, handler))
    }

    fun <I : State> addStrict(kClass: KClass<I>, handler: StatesHandler<I>) {
        states.add(StatesHandlerHolder(kClass, true, handler))
    }

    fun build() = StatesMachine(
        statesManager,
        states.toList().let { list ->
            defaultStateHandler ?.let { list + it.holder(false) } ?: list
        }
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
