package dev.inmo.micro_utils.pagination

import kotlinx.serialization.Serializable

const val defaultSmallPageSize = 2
const val defaultMediumPageSize = 5
const val defaultLargePageSize = 10
const val defaultExtraLargePageSize = 15

var defaultPaginationPageSize = defaultMediumPageSize

@Suppress("NOTHING_TO_INLINE", "FunctionName")
inline fun FirstPagePagination(size: Int = defaultPaginationPageSize) =
    SimplePagination(
        page = 0,
        size = size
    )

val emptyPagination = Pagination(0, 0)
val firstPageWithOneElementPagination = FirstPagePagination(1)

@Suppress("NOTHING_TO_INLINE")
inline fun Pagination.nextPage() =
    SimplePagination(
        page + 1,
        size
    )

@Serializable
data class SimplePagination(
    override val page: Int,
    override val size: Int
) : Pagination

/**
 * Factory for [SimplePagination]
 */
fun Pagination(
    page: Int,
    size: Int
) = SimplePagination(page, size)

/**
 * @param firstIndex Inclusive first index of pagination
 * @param lastIndex INCLUSIVE last index of pagination (last index of object covered by result [SimplePagination])
 */
fun PaginationByIndexes(
    firstIndex: Int,
    lastIndex: Int
) = maxOf(0, (lastIndex - firstIndex + 1)).let { size ->
    Pagination(calculatePage(firstIndex, size), size)
}
