package dev.inmo.micro_utils.coroutines.compose

import androidx.compose.runtime.*
import dev.inmo.micro_utils.coroutines.ExceptionHandler
import dev.inmo.micro_utils.coroutines.defaultSafelyWithoutExceptionHandlerWithNull
import dev.inmo.micro_utils.coroutines.doInUI
import dev.inmo.micro_utils.coroutines.subscribeSafelyWithoutExceptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

fun <T> Flow<T>.asMutableComposeState(
    initial: T,
    scope: CoroutineScope,
    useContextOnChange: CoroutineContext? = Dispatchers.Main,
    onException: ExceptionHandler<T?> = defaultSafelyWithoutExceptionHandlerWithNull,
): MutableState<T> {
    val state = mutableStateOf(initial)
    val changeBlock: suspend (T) -> Unit = useContextOnChange ?.let {
        {
            withContext(useContextOnChange) {
                state.value = it
            }
        }
    } ?: {
        state.value = it
    }
    subscribeSafelyWithoutExceptions(scope, onException, block = changeBlock)
    return state
}

@Suppress("NOTHING_TO_INLINE")
inline fun <T> StateFlow<T>.asMutableComposeState(
    scope: CoroutineScope,
    useContextOnChange: CoroutineContext? = Dispatchers.Main,
    noinline onException: ExceptionHandler<T?> = defaultSafelyWithoutExceptionHandlerWithNull,
): MutableState<T> = asMutableComposeState(value, scope, useContextOnChange, onException)

fun <T> Flow<T>.asComposeState(
    initial: T,
    scope: CoroutineScope,
    useContextOnChange: CoroutineContext? = Dispatchers.Main,
    onException: ExceptionHandler<T?> = defaultSafelyWithoutExceptionHandlerWithNull,
): State<T> {
    val state = asMutableComposeState(initial, scope, useContextOnChange, onException)
    return derivedStateOf { state.value }
}

@Suppress("NOTHING_TO_INLINE")
inline fun <T> StateFlow<T>.asComposeState(
    scope: CoroutineScope,
    useContextOnChange: CoroutineContext? = Dispatchers.Main,
    noinline onException: ExceptionHandler<T?> = defaultSafelyWithoutExceptionHandlerWithNull,
): State<T> = asComposeState(value, scope, useContextOnChange, onException)

