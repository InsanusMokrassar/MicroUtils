package dev.inmo.micro_utils.common.compose

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf

fun <T> MutableState<T>.asState() = derivedStateOf { this.value }
