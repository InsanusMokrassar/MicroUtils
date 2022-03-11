package dev.inmo.micro_utils.coroutines.compose

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import dev.inmo.micro_utils.coroutines.subscribeSafelyWithoutExceptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

fun <T> Flow<T>.toMutableState(
    initial: T,
    scope: CoroutineScope
): MutableState<T> {
    val state = mutableStateOf(initial)
    subscribeSafelyWithoutExceptions(scope) { state.value = it }
    return state
}

inline fun <T> StateFlow<T>.toMutableState(
    scope: CoroutineScope
): MutableState<T> = toMutableState(value, scope)

