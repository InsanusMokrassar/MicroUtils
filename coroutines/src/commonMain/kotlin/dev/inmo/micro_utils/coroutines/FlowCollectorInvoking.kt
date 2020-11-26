package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.flow.FlowCollector

suspend inline operator fun <T> FlowCollector<T>.invoke(value: T) = emit(value)
