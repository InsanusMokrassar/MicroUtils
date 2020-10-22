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
    if (firstIndex > objectsCount) {
        return Pagination(objectsCount.toInt(), size)
    }
    val firstIndex = (objectsCount - firstIndex - size).toInt()
    return Pagination(
        firstIndex,
        size
    )
}
