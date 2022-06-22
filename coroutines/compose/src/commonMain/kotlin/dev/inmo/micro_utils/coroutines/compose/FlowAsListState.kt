package dev.inmo.micro_utils.coroutines.compose

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import dev.inmo.micro_utils.common.applyDiff
import dev.inmo.micro_utils.coroutines.subscribeSafelyWithoutExceptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Suppress("NOTHING_TO_INLINE")
inline fun <reified T> Flow<List<T>>.asMutableComposeListState(
    scope: CoroutineScope
): SnapshotStateList<T> {
    val state = mutableStateListOf<T>()
    subscribeSafelyWithoutExceptions(scope) {
        state.applyDiff(it)
    }
    return state
}

@Suppress("NOTHING_TO_INLINE")
inline fun <reified T> Flow<List<T>>.asComposeList(
    scope: CoroutineScope
): List<T> = asMutableComposeListState(scope)

