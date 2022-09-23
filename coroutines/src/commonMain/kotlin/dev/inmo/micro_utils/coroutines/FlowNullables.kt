package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.flow.*

fun <T> Flow<T>.takeNotNull() = mapNotNull { it }
fun <T> Flow<T>.filterNotNull() = takeNotNull()
