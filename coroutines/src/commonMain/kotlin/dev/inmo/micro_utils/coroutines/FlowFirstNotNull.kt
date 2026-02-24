package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

/**
 * Returns the first non-null element emitted by this [Flow].
 * Suspends until a non-null element is found.
 *
 * @param T The type of elements in the flow
 * @return The first non-null element
 * @throws NoSuchElementException if the flow completes without emitting a non-null element
 */
suspend fun <T> Flow<T?>.firstNotNull() = first { it != null }!!
