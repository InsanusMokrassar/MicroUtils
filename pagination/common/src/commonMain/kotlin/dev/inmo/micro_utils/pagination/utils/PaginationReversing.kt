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
    val resultSize = minOf(size, objectsCount.toInt())
    return when {
        firstIndex > objectsCount -> Pagination(calculatePage(resultSize, resultSize), resultSize)
        size > objectsCount -> FirstPagePagination(resultSize)
        else -> {
            val firstIndex = (objectsCount - firstIndex - resultSize).toInt()
            Pagination(
                firstIndex,
                resultSize
            )
        }
    }
}
