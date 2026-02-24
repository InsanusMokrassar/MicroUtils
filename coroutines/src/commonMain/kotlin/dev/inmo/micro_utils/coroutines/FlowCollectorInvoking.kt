package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.flow.FlowCollector

/**
 * Operator function that allows a [FlowCollector] to be invoked like a function to emit a value.
 * This is a convenient syntax sugar for [FlowCollector.emit].
 *
 * @param T The type of values the collector can emit
 * @param value The value to emit
 */
suspend inline operator fun <T> FlowCollector<T>.invoke(value: T) = emit(value)
