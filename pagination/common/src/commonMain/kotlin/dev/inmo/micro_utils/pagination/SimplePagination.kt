package dev.inmo.micro_utils.pagination

import kotlinx.serialization.Serializable

const val defaultSmallPageSize = 2
const val defaultMediumPageSize = 5
const val defaultLargePageSize = 10
const val defaultExtraLargePageSize = 15

@Suppress("NOTHING_TO_INLINE", "FunctionName")
inline fun FirstPagePagination(size: Int = defaultMediumPageSize) =
    SimplePagination(
        page = 0,
        size = size
    )

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

fun Pagination(
    page: Int,
    size: Int
) = SimplePagination(page, size)
