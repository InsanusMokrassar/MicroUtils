package dev.inmo.micro_utils.coroutines.compose

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import dev.inmo.micro_utils.common.applyDiff
import dev.inmo.micro_utils.coroutines.ExceptionHandler
import dev.inmo.micro_utils.coroutines.defaultSafelyWithoutExceptionHandlerWithNull
import dev.inmo.micro_utils.coroutines.subscribeSafelyWithoutExceptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

@Suppress("NOTHING_TO_INLINE")
inline fun <reified T> Flow<List<T>>.asMutableComposeListState(
    scope: CoroutineScope,
    useContextOnChange: CoroutineContext? = Dispatchers.Main,
    noinline onException: ExceptionHandler<List<T>?> = defaultSafelyWithoutExceptionHandlerWithNull,
): SnapshotStateList<T> {
    val state = mutableStateListOf<T>()
    val changeBlock: suspend (List<T>) -> Unit = useContextOnChange ?.let {
        {
            withContext(useContextOnChange) {
                state.applyDiff(it)
            }
        }
    } ?: {
        state.applyDiff(it)
    }
    subscribeSafelyWithoutExceptions(scope, onException, changeBlock)
    return state
}

@Suppress("NOTHING_TO_INLINE")
inline fun <reified T> Flow<List<T>>.asComposeList(
    scope: CoroutineScope,
    useContextOnChange: CoroutineContext? = Dispatchers.Main,
    noinline onException: ExceptionHandler<List<T>?> = defaultSafelyWithoutExceptionHandlerWithNull,
): List<T> = asMutableComposeListState(scope, useContextOnChange, onException)

