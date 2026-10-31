package dev.inmo.micro_utils.pagination.utils

import dev.inmo.micro_utils.pagination.*

/**
 * Paginates this [Iterable] according to the given [Pagination] parameters.
 * Returns a [PaginationResult] containing the items within the specified page range.
 *
 * @param T The type of items in the iterable
 * @param with The pagination parameters specifying which page to retrieve
 * @return A [PaginationResult] containing the items from the requested page
 */
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

/**
 * Paginates this [List] according to the given [Pagination] parameters.
 * Returns a [PaginationResult] containing the items within the specified page range.
 * More efficient than the [Iterable] version as it uses direct indexing.
 *
 * @param T The type of items in the list
 * @param with The pagination parameters specifying which page to retrieve
 * @return A [PaginationResult] containing the items from the requested page
 */
fun <T> List<T>.paginate(with: Pagination): PaginationResult<T> {
    if (with.firstIndex >= size || with.lastIndex < 0) {
        return emptyPaginationResult(with, size.toLong())
    }
    return asSequence().drop(with.firstIndex).take(with.size).toList().createPaginationResult(
        with,
        size.toLong()
    )
}

/**
 * Paginates this [List] according to the given [Pagination] parameters, optionally in reverse order.
 *
 * @param T The type of items in the list
 * @param with The pagination parameters specifying which page to retrieve
 * @param reversed If true, the list will be paginated in reverse order
 * @return A [PaginationResult] containing the items from the requested page, optionally reversed
 */
fun <T> List<T>.paginate(with: Pagination, reversed: Boolean): PaginationResult<T> {
    return if (reversed) {
        val actualPagination = with.optionallyReverse(
            size,
            reversed
        )
        paginate(actualPagination).changeResultsUnchecked { results.reversed() }
    } else {
        paginate(with)
    }
}

/**
 * Paginates this [Set] according to the given [Pagination] parameters.
 * Returns a [PaginationResult] containing the items within the specified page range.
 *
 * @param T The type of items in the set
 * @param with The pagination parameters specifying which page to retrieve
 * @return A [PaginationResult] containing the items from the requested page
 */
fun <T> Set<T>.paginate(with: Pagination): PaginationResult<T> {
    return this.drop(with.firstIndex).take(with.size).createPaginationResult(
        with,
        size.toLong()
    )
}

/**
 * Paginates this [Set] according to the given [Pagination] parameters, optionally in reverse order.
 *
 * @param T The type of items in the set
 * @param with The pagination parameters specifying which page to retrieve
 * @param reversed If true, the set will be paginated in reverse order
 * @return A [PaginationResult] containing the items from the requested page, optionally reversed
 */
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
