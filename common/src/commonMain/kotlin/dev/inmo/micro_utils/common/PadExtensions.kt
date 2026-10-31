package dev.inmo.micro_utils.common

/**
 * Pads this sequence to the specified [size] using a custom [inserter] function.
 * The [inserter] is repeatedly called until the sequence reaches the desired size.
 *
 * @param T The type of elements in the sequence
 * @param size The target size of the padded sequence
 * @param inserter A function that takes the current sequence and returns a new padded sequence
 * @return A sequence padded to at least the specified size
 */
inline fun <T> Sequence<T>.padWith(size: Int, inserter: (Sequence<T>) -> Sequence<T>): Sequence<T> {
    var result = this
    while (result.count() < size) {
        result = inserter(result)
    }
    return result
}

/**
 * Pads this sequence at the end to the specified [size].
 * New elements are generated using [padBlock], which receives the current size as a parameter.
 *
 * @param T The type of elements in the sequence
 * @param size The target size of the padded sequence
 * @param padBlock A function that generates padding elements based on the current sequence size
 * @return A sequence padded to at least the specified size
 */
inline fun <T> Sequence<T>.padEnd(size: Int, padBlock: (Int) -> T): Sequence<T> = padWith(size) { it + padBlock(it.count()) }

/**
 * Pads this sequence at the end to the specified [size] using the given element [o].
 *
 * @param T The type of elements in the sequence
 * @param size The target size of the padded sequence
 * @param o The element to use for padding
 * @return A sequence padded to at least the specified size
 */
inline fun <T> Sequence<T>.padEnd(size: Int, o: T) = padEnd(size) { o }

/**
 * Pads this list to the specified [size] using a custom [inserter] function.
 * The [inserter] is repeatedly called until the list reaches the desired size.
 *
 * @param T The type of elements in the list
 * @param size The target size of the padded list
 * @param inserter A function that takes the current list and returns a new padded list
 * @return A list padded to at least the specified size
 */
inline fun <T> List<T>.padWith(size: Int, inserter: (List<T>) -> List<T>): List<T> {
    var result = this
    while (result.size < size) {
        result = inserter(result)
    }
    return result
}

/**
 * Pads this list at the end to the specified [size].
 * New elements are generated using [padBlock], which receives the current size as a parameter.
 *
 * @param T The type of elements in the list
 * @param size The target size of the padded list
 * @param padBlock A function that generates padding elements based on the current list size
 * @return A list padded to at least the specified size
 */
inline fun <T> List<T>.padEnd(size: Int, padBlock: (Int) -> T): List<T> = asSequence().padEnd(size, padBlock).toList()

/**
 * Pads this list at the end to the specified [size] using the given element [o].
 *
 * @param T The type of elements in the list
 * @param size The target size of the padded list
 * @param o The element to use for padding
 * @return A list padded to at least the specified size
 */
inline fun <T> List<T>.padEnd(size: Int, o: T): List<T> = asSequence().padEnd(size, o).toList()

/**
 * Pads this sequence at the start to the specified [size].
 * New elements are generated using [padBlock], which receives the current size as a parameter.
 *
 * @param T The type of elements in the sequence
 * @param size The target size of the padded sequence
 * @param padBlock A function that generates padding elements based on the current sequence size
 * @return A sequence padded to at least the specified size
 */
inline fun <T> Sequence<T>.padStart(size: Int, padBlock: (Int) -> T): Sequence<T> = padWith(size) { sequenceOf(padBlock(it.count())) + it }

/**
 * Pads this sequence at the start to the specified [size] using the given element [o].
 *
 * @param T The type of elements in the sequence
 * @param size The target size of the padded sequence
 * @param o The element to use for padding
 * @return A sequence padded to at least the specified size
 */
inline fun <T> Sequence<T>.padStart(size: Int, o: T) = padStart(size) { o }

/**
 * Pads this list at the start to the specified [size].
 * New elements are generated using [padBlock], which receives the current size as a parameter.
 *
 * @param T The type of elements in the list
 * @param size The target size of the padded list
 * @param padBlock A function that generates padding elements based on the current list size
 * @return A list padded to at least the specified size
 */
inline fun <T> List<T>.padStart(size: Int, padBlock: (Int) -> T): List<T> = asSequence().padStart(size, padBlock).toList()

/**
 * Pads this list at the start to the specified [size] using the given element [o].
 *
 * @param T The type of elements in the list
 * @param size The target size of the padded list
 * @param o The element to use for padding
 * @return A list padded to at least the specified size
 */
inline fun <T> List<T>.padStart(size: Int, o: T): List<T> = asSequence().padStart(size, o).toList()
