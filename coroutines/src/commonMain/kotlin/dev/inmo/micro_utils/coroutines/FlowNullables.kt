package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.flow.*

/**
 * Filters out null values from this [Flow], returning only non-null elements.
 *
 * @param T The type of elements in the flow (nullable)
 * @return A [Flow] containing only non-null elements
 */
fun <T> Flow<T>.takeNotNull() = mapNotNull { it }

/**
 * Alias for [takeNotNull]. Filters out null values from this [Flow], returning only non-null elements.
 *
 * @param T The type of elements in the flow (nullable)
 * @return A [Flow] containing only non-null elements
 */
fun <T> Flow<T>.filterNotNull() = takeNotNull()
