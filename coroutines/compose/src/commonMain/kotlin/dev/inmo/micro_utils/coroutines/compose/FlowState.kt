package dev.inmo.micro_utils.coroutines.compose

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import dev.inmo.micro_utils.coroutines.SpecialMutableStateFlow
import dev.inmo.micro_utils.coroutines.doInUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class FlowState<T>(
    private val state: MutableState<T>,
    internalScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
) : MutableState<T>,
    SpecialMutableStateFlow.Default<T>(state.value, internalScope) {
    override var value: T
        get() = super.value
        set(value) {
            tryEmit(value)
        }

    override fun component1(): T = state.component1()

    override fun component2(): (T) -> Unit = state.component2()

    private val stateState = derivedStateOf {
        if (value != state.value) {
            value = state.value
        }
    }

    override suspend fun onChange(value: T) {
        super.onChange(value)

        if (state.value != value) {
            doInUI {
                state.value = value
            }
        }
    }
}

fun <T> MutableState<T>.asFlowState(scope: CoroutineScope = CoroutineScope(Dispatchers.Main)) = FlowState(this, scope)
