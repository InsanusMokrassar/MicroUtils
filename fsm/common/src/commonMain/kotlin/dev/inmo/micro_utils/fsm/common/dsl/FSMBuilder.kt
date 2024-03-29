package dev.inmo.micro_utils.fsm.common.dsl

import dev.inmo.micro_utils.fsm.common.*
import dev.inmo.micro_utils.fsm.common.managers.DefaultStatesManager
import dev.inmo.micro_utils.fsm.common.managers.InMemoryDefaultStatesManagerRepo
import kotlin.reflect.KClass

class FSMBuilder<T : State>(
    var statesManager: StatesManager<T> = DefaultStatesManager(InMemoryDefaultStatesManagerRepo()),
    val fsmBuilder: (statesManager: StatesManager<T>, states: List<CheckableHandlerHolder<T, T>>) -> StatesMachine<T> = { statesManager, states ->
        StatesMachine(
            statesManager,
            states
        )
    },
    var defaultStateHandler: StatesHandler<T, T>? = StatesHandler { null }
) {
    private var states = mutableListOf<CheckableHandlerHolder<T, T>>()

    fun add(handler: CheckableHandlerHolder<T, T>) {
        states.add(handler)
    }

    fun <I : T> add(kClass: KClass<I>, handler: StatesHandler<I, T>) {
        add(CheckableHandlerHolder(kClass, false, handler))
    }

    fun <I : T> add(filter: suspend (state: State) -> Boolean, handler: StatesHandler<I, T>) {
        add(handler.holder(filter))
    }

    fun <I : T> addStrict(kClass: KClass<I>, handler: StatesHandler<I, T>) {
        states.add(CheckableHandlerHolder(kClass, true, handler))
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

    fun build() = fsmBuilder(
        statesManager,
        states.toList().let { list ->
            defaultStateHandler ?.let { list + it.holder { true } } ?: list
        }
    )
}

fun <T : State> buildFSM(
    block: FSMBuilder<T>.() -> Unit
): StatesMachine<T> = FSMBuilder<T>().apply(block).build()
