package dev.inmo.micro_utils.common.compose

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf

/**
 * Converts current [MutableState] to immutable [State] using [derivedStateOf]
 */
fun <T> MutableState<T>.asState(): State<T> = derivedStateOf { this.value }
