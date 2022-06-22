package dev.inmo.micro_utils.coroutines.compose

import androidx.compose.runtime.*
import dev.inmo.micro_utils.coroutines.subscribeSafelyWithoutExceptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

fun <T> Flow<T>.asMutableComposeState(
    initial: T,
    scope: CoroutineScope
): MutableState<T> {
    val state = mutableStateOf(initial)
    subscribeSafelyWithoutExceptions(scope) { state.value = it }
    return state
}

@Suppress("NOTHING_TO_INLINE")
inline fun <T> StateFlow<T>.asMutableComposeState(
    scope: CoroutineScope
): MutableState<T> = asMutableComposeState(value, scope)

fun <T> Flow<T>.asComposeState(
    initial: T,
    scope: CoroutineScope
): State<T> {
    val state = asMutableComposeState(initial, scope)
    return derivedStateOf { state.value }
}

@Suppress("NOTHING_TO_INLINE")
inline fun <T> StateFlow<T>.asComposeState(
    scope: CoroutineScope
): State<T> = asComposeState(value, scope)

