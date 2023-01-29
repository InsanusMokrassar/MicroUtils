package dev.inmo.micro_utils.coroutines.compose

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import dev.inmo.micro_utils.coroutines.subscribeSafelyWithoutExceptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Deprecated("Duplicated functionality", ReplaceWith("asMutableComposeState(initial, scope)", "dev.inmo.micro_utils.coroutines.compose.asMutableComposeState"))
fun <T> Flow<T>.toMutableState(
    initial: T,
    scope: CoroutineScope
): MutableState<T> = asMutableComposeState(initial, scope)

@Deprecated("Duplicated functionality", ReplaceWith("asMutableComposeState(scope)", "dev.inmo.micro_utils.coroutines.compose.asMutableComposeState"))
@Suppress("NOTHING_TO_INLINE")
inline fun <T> StateFlow<T>.toMutableState(
    scope: CoroutineScope
): MutableState<T> = asMutableComposeState(scope)

