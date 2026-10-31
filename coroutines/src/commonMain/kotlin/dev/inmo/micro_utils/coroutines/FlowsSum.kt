@file:Suppress("NOTHING_TO_INLINE")

package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge

/**
 * Merges two flows into a single flow. Values from both flows will be emitted as they become available.
 * This is a convenient operator syntax for [merge].
 *
 * @param T The type of elements in the flows
 * @param other The flow to merge with this flow
 * @return A [Flow] that emits values from both flows
 */
inline operator fun <T> Flow<T>.plus(other: Flow<T>) = merge(this, other)
