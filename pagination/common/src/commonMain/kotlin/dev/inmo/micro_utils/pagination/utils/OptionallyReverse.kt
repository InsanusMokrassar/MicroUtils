package dev.inmo.micro_utils.pagination.utils

fun <T> Iterable<T>.optionallyReverse(reverse: Boolean): Iterable<T> = when (this) {
    is List<T> -> optionallyReverse(reverse)
    is Set<T> -> optionallyReverse(reverse)
    else -> if (reverse) {
        reversed()
    } else {
        this
    }
}
fun <T> List<T>.optionallyReverse(reverse: Boolean): List<T> = if (reverse) {
    reversed()
} else {
    this
}
fun <T> Set<T>.optionallyReverse(reverse: Boolean): Set<T> = if (reverse) {
    reversed().toSet()
} else {
    this
}

inline fun <reified T> Array<T>.optionallyReverse(reverse: Boolean) = if (reverse) {
    Array(size) {
        get(lastIndex - it)
    }
} else {
    this
}
