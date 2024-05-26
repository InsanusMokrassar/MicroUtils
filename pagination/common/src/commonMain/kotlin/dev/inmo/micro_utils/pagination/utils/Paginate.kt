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
    val indices = indices
    val firstIndex = with.firstIndex.coerceIn(indices)
    val lastIndex = with.lastIndex.coerceIn(indices) + 1 // up to size
    if (firstIndex > lastIndex) {
        return emptyPaginationResult()
    }
    return subList(firstIndex, lastIndex).createPaginationResult(
        with,
        size.toLong()
    )
}

fun <T> List<T>.paginate(with: Pagination, reversed: Boolean): PaginationResult<T> {
    val actualPagination = with.optionallyReverse(
        size,
        reversed
    )

    val firstIndex = actualPagination.firstIndex.coerceIn(indices)
    val lastIndex = actualPagination.lastIndex.coerceIn(indices) + 1 // up to size
    if (firstIndex > lastIndex) {
        return emptyPaginationResult()
    }

    return subList(firstIndex, lastIndex).optionallyReverse(reversed).createPaginationResult(
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

fun <T> Set<T>.paginate(with: Pagination, reversed: Boolean): PaginationResult<T> {
    val actualPagination = with.optionallyReverse(
        size,
        reversed
    )

    val firstIndex = maxOf(actualPagination.firstIndex, 0)
    val lastIndex = minOf(actualPagination.lastIndexExclusive, size)
    if (firstIndex > lastIndex) {
        return emptyPaginationResult()
    }

    return this.drop(firstIndex).take(lastIndex - firstIndex).optionallyReverse(reversed).createPaginationResult(
        with,
        size.toLong()
    )
}
