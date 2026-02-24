package dev.inmo.micro_utils.pagination.utils

/**
 * Optionally reverses this [Iterable] based on the [reverse] parameter.
 * Delegates to specialized implementations for [List] and [Set] for better performance.
 *
 * @param T The type of items in the iterable
 * @param reverse If true, reverses the iterable; otherwise returns it unchanged
 * @return The iterable, optionally reversed
 */
fun <T> Iterable<T>.optionallyReverse(reverse: Boolean): Iterable<T> = when (this) {
    is List<T> -> optionallyReverse(reverse)
    is Set<T> -> optionallyReverse(reverse)
    else -> if (reverse) {
        reversed()
    } else {
        this
    }
}

/**
 * Optionally reverses this [List] based on the [reverse] parameter.
 *
 * @param T The type of items in the list
 * @param reverse If true, reverses the list; otherwise returns it unchanged
 * @return The list, optionally reversed
 */
fun <T> List<T>.optionallyReverse(reverse: Boolean): List<T> = if (reverse) {
    reversed()
} else {
    this
}

/**
 * Optionally reverses this [Set] based on the [reverse] parameter.
 * Note that the resulting set may have a different iteration order than the original.
 *
 * @param T The type of items in the set
 * @param reverse If true, reverses the set; otherwise returns it unchanged
 * @return The set, optionally reversed
 */
fun <T> Set<T>.optionallyReverse(reverse: Boolean): Set<T> = if (reverse) {
    reversed().toSet()
} else {
    this
}

/**
 * Optionally reverses this [Array] based on the [reverse] parameter.
 *
 * @param T The type of items in the array
 * @param reverse If true, creates a reversed copy of the array; otherwise returns it unchanged
 * @return The array, optionally reversed
 */
inline fun <reified T> Array<T>.optionallyReverse(reverse: Boolean) = if (reverse) {
    Array(size) {
        get(lastIndex - it)
    }
} else {
    this
}
