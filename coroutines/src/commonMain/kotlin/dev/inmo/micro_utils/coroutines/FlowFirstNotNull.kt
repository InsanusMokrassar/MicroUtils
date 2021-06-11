package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

suspend fun <T> Flow<T?>.firstNotNull() = first { it != null }!!
