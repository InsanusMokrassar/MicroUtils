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

/**
 * Each value of [this] [Flow] will trigger [applyDiff] to the result [SnapshotStateList]
 *
 * @param scope Will be used to [subscribeSafelyWithoutExceptions] on [this] to update returned [SnapshotStateList]
 * @param useContextOnChange Will be used to change context inside of [subscribeSafelyWithoutExceptions] to ensure that
 * change will happen in the required [CoroutineContext]. [Dispatchers.Main] by default
 * @param onException Will be passed to the [subscribeSafelyWithoutExceptions] as uncaught exceptions handler
 */
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

/**
 * In fact, it is just classcast of [asMutableComposeListState] to [List]
 *
 * @param scope Will be used to [subscribeSafelyWithoutExceptions] on [this] to update returned [List]
 * @param useContextOnChange Will be used to change context inside of [subscribeSafelyWithoutExceptions] to ensure that
 * change will happen in the required [CoroutineContext]. [Dispatchers.Main] by default
 * @param onException Will be passed to the [subscribeSafelyWithoutExceptions] as uncaught exceptions handler
 *
 * @return Changing in time [List] which follow [Flow] values
 */
@Suppress("NOTHING_TO_INLINE")
inline fun <reified T> Flow<List<T>>.asComposeList(
    scope: CoroutineScope,
    useContextOnChange: CoroutineContext? = Dispatchers.Main,
    noinline onException: ExceptionHandler<List<T>?> = defaultSafelyWithoutExceptionHandlerWithNull,
): List<T> = asMutableComposeListState(scope, useContextOnChange, onException)

