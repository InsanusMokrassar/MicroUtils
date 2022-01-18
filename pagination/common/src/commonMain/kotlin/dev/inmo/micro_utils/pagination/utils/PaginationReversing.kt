package dev.inmo.micro_utils.pagination.utils

import dev.inmo.micro_utils.pagination.*

/**
 * Example:
 *
 * * `|__f__l_______________________|` will be transformed to `|_______________________f__l__|`
 * * `|__f__l_|` will be transformed to `|__f__l_|`
 *
 * @return Reversed version of this [Pagination]
 */
fun Pagination.reverse(datasetSize: Long): SimplePagination {
    val pagesNumber = calculatePagesNumber(size, datasetSize)
    val newPage = pagesNumber - page - 1
    return when {
        page < 0 || page >= pagesNumber -> emptyPagination
        else -> Pagination(
            newPage,
            size
        )
    }
}

/**
 * Shortcut for [reverse]
 */
fun Pagination.reverse(objectsCount: Int) = reverse(objectsCount.toLong())

fun Pagination.optionallyReverse(objectsCount: Int, reverse: Boolean) = if (reverse) {
    reverse(objectsCount)
} else {
    this
}

fun Pagination.optionallyReverse(objectsCount: Long, reverse: Boolean) = if (reverse) {
    reverse(objectsCount)
} else {
    this
}
