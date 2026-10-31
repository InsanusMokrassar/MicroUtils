package dev.inmo.micro_utils.common

/**
 * Returns a new list with the element at index [i] replaced by applying [block] to it.
 * All other elements remain unchanged.
 *
 * @param T The type of elements in the iterable
 * @param i The index of the element to replace
 * @param block A function that transforms the element at the given index
 * @return A new list with the replaced element
 */
fun <T> Iterable<T>.withReplacedAt(i: Int, block: (T) -> T): List<T> = take(i) + block(elementAt(i)) + drop(i + 1)

/**
 * Returns a new list with the first occurrence of element [t] replaced by applying [block] to it.
 * All other elements remain unchanged.
 *
 * @param T The type of elements in the iterable
 * @param t The element to replace
 * @param block A function that transforms the found element
 * @return A new list with the replaced element
 */
fun <T> Iterable<T>.withReplaced(t: T, block: (T) -> T): List<T> = withReplacedAt(indexOf(t), block)

