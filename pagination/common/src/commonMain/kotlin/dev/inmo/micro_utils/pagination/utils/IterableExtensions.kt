package dev.inmo.micro_utils.pagination.utils

import dev.inmo.micro_utils.pagination.*

fun <T> Iterable<T>.paginate(with: Pagination): PaginationResult<T> {
    var i = 0
    val result = mutableListOf<T>()
    val lowerIndex = with.firstIndex
    val greatestIndex = with.lastIndex
    for (item in this) {
        when {
            i < lowerIndex || i > greatestIndex -> i++
            else -> {
                result.add(item)
                i++
            }
        }
    }

    return result.createPaginationResult(with, i.toLong())
}

fun <T> List<T>.paginate(with: Pagination): PaginationResult<T> {
    val firstIndex = maxOf(with.firstIndex, 0)
    val lastIndex = minOf(with.lastIndexExclusive, size)
    if (firstIndex > lastIndex) {
        return emptyPaginationResult()
    }
    return subList(firstIndex, lastIndex).createPaginationResult(
        with,
        size.toLong()
    )
}

fun <T> Set<T>.paginate(with: Pagination): PaginationResult<T> {
    return this.drop(with.firstIndex).take(with.size).createPaginationResult(
        with,
        size.toLong()
    )
}

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
