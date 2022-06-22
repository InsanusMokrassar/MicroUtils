package dev.inmo.micro_utils.coroutines.compose

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import dev.inmo.micro_utils.common.applyDiff
import dev.inmo.micro_utils.coroutines.subscribeSafelyWithoutExceptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Suppress("NOTHING_TO_INLINE")
inline fun <reified T> StateFlow<List<T>>.toMutableListState(
    scope: CoroutineScope
): SnapshotStateList<T> {
    val state = mutableStateListOf(*value.toTypedArray())
    subscribeSafelyWithoutExceptions(scope) {
        state.applyDiff(it)
    }
    return state
}

