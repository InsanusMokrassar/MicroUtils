package dev.inmo.micro_utils.common

/**
 * Returns the first non-null element in this iterable.
 *
 * @param T The type of elements in the iterable (nullable)
 * @return The first non-null element
 * @throws NoSuchElementException if the iterable contains no non-null elements
 */
fun <T> Iterable<T?>.firstNotNull() = first { it != null }!!
