package dev.inmo.micro_utils.coroutines.compose

import androidx.compose.runtime.*
import dev.inmo.micro_utils.common.compose.asState
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

/**
 * Will map [this] [Flow] as [MutableState]. Returned [MutableState] WILL NOT change source [Flow]
 *
 * @param initial First value which will be passed to the result [MutableState]
 * @param scope Will be used to [subscribeSafelyWithoutExceptions] on [this] to update returned [MutableState]
 * @param useContextOnChange Will be used to change context inside of [subscribeSafelyWithoutExceptions] to ensure that
 * change will happen in the required [CoroutineContext]. [Dispatchers.Main] by default
 * @param onException Will be passed to the [subscribeSafelyWithoutExceptions] as uncaught exceptions handler
 */
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

/**
 * Will map [this] [StateFlow] as [MutableState]. Returned [MutableState] WILL NOT change source [StateFlow].
 * This conversation will pass its [StateFlow.value] as the first value
 *
 * @param scope Will be used to [subscribeSafelyWithoutExceptions] on [this] to update returned [MutableState]
 * @param useContextOnChange Will be used to change context inside of [subscribeSafelyWithoutExceptions] to ensure that
 * change will happen in the required [CoroutineContext]. [Dispatchers.Main] by default
 * @param onException Will be passed to the [subscribeSafelyWithoutExceptions] as uncaught exceptions handler
 */
@Suppress("NOTHING_TO_INLINE")
inline fun <T> StateFlow<T>.asMutableComposeState(
    scope: CoroutineScope,
    useContextOnChange: CoroutineContext? = Dispatchers.Main,
    noinline onException: ExceptionHandler<T?> = defaultSafelyWithoutExceptionHandlerWithNull,
): MutableState<T> = asMutableComposeState(value, scope, useContextOnChange, onException)

/**
 * Will create [MutableState] using [asMutableComposeState] and use [asState] to convert it as immutable state
 *
 * @param initial First value which will be passed to the result [State]
 * @param scope Will be used to [subscribeSafelyWithoutExceptions] on [this] to update returned [State]
 * @param useContextOnChange Will be used to change context inside of [subscribeSafelyWithoutExceptions] to ensure that
 * change will happen in the required [CoroutineContext]. [Dispatchers.Main] by default
 * @param onException Will be passed to the [subscribeSafelyWithoutExceptions] as uncaught exceptions handler
 */
fun <T> Flow<T>.asComposeState(
    initial: T,
    scope: CoroutineScope,
    useContextOnChange: CoroutineContext? = Dispatchers.Main,
    onException: ExceptionHandler<T?> = defaultSafelyWithoutExceptionHandlerWithNull,
): State<T> {
    val state = asMutableComposeState(initial, scope, useContextOnChange, onException)
    return state.asState()
}

/**
 * Will map [this] [StateFlow] as [State]. This conversation will pass its [StateFlow.value] as the first value
 *
 * @param scope Will be used to [subscribeSafelyWithoutExceptions] on [this] to update returned [State]
 * @param useContextOnChange Will be used to change context inside of [subscribeSafelyWithoutExceptions] to ensure that
 * change will happen in the required [CoroutineContext]. [Dispatchers.Main] by default
 * @param onException Will be passed to the [subscribeSafelyWithoutExceptions] as uncaught exceptions handler
 */
@Suppress("NOTHING_TO_INLINE")
inline fun <T> StateFlow<T>.asComposeState(
    scope: CoroutineScope,
    useContextOnChange: CoroutineContext? = Dispatchers.Main,
    noinline onException: ExceptionHandler<T?> = defaultSafelyWithoutExceptionHandlerWithNull,
): State<T> = asComposeState(value, scope, useContextOnChange, onException)

