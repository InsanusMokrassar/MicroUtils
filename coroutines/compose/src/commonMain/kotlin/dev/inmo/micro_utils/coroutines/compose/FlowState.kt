package dev.inmo.micro_utils.coroutines.compose

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import dev.inmo.micro_utils.coroutines.SpecialMutableStateFlow
import dev.inmo.micro_utils.coroutines.doInUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

/**
 * This type works like [MutableState], [kotlinx.coroutines.flow.StateFlow] and [kotlinx.coroutines.flow.MutableSharedFlow].
 * Based on [SpecialMutableStateFlow]
 */
class FlowState<T>(
    initial: T,
    internalScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) : MutableState<T>,
    SpecialMutableStateFlow<T>(initial, internalScope) {
    private var internalValue: T = initial
    override var value: T
        get() = internalValue
        set(value) {
            internalValue = value
            tryEmit(value)
        }

    override suspend fun onChange(value: T) {
        internalValue = value
        super.onChange(value)
    }

    override fun component1(): T = value

    override fun component2(): (T) -> Unit = { tryEmit(it) }

    override fun tryEmit(value: T): Boolean {
        internalValue = value
        return super.tryEmit(value)
    }

    override suspend fun emit(value: T) {
        internalValue = value
        super.emit(value)
    }
}

//fun <T> MutableState<T>.asFlowState(scope: CoroutineScope = CoroutineScope(Dispatchers.Main)) = FlowState(this, scope)
