package dev.inmo.micro_utils.pagination

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

const val defaultSmallPageSize = 2
const val defaultMediumPageSize = 5
const val defaultLargePageSize = 10
const val defaultExtraLargePageSize = 15

@JsExport
@Suppress("NOTHING_TO_INLINE", "FunctionName")
inline fun FirstPagePagination(size: Int = defaultMediumPageSize) =
    SimplePagination(
        page = 0,
        size = size
    )

@JsExport
@Suppress("NOTHING_TO_INLINE")
inline fun Pagination.nextPage() =
    SimplePagination(
        page + 1,
        size
    )

@JsExport
@Serializable
data class SimplePagination(
    override val page: Int,
    override val size: Int
) : Pagination

@JsExport
fun Pagination(
    page: Int,
    size: Int
) = SimplePagination(page, size)
