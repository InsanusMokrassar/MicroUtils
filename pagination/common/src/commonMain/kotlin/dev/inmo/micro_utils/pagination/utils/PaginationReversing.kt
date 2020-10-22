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
fun Pagination.reverse(objectsCount: Long): SimplePagination {
    val firstIndex = (objectsCount - (this.lastIndex + 1)).let {
        when {
            it < 0 -> 0
            it >= objectsCount -> return emptyPagination
            else -> it
        }
    }.toInt()
    val lastIndex = (objectsCount - (this.firstIndex + 1)).let {
        when {
            it < 0 -> return emptyPagination
            it >= objectsCount -> objectsCount - 1
            else -> it
        }
    }.toInt()
    return PaginationByIndexes(firstIndex, lastIndex)
}

/**
 * Shortcut for [reverse]
 */
fun Pagination.reverse(objectsCount: Int) = reverse(objectsCount.toLong())
