package dev.inmo.micro_utils.pagination

import kotlinx.serialization.Serializable
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
@Serializable
data class PaginationResult<T>(
    override val page: Int,
    val pagesNumber: Int,
    val results: List<T>,
    override val size: Int
) : Pagination

@JsExport
fun <T> emptyPaginationResult() = PaginationResult<T>(0, 0, emptyList(), 0)

@JsExport
@JsName("createPaginationResultWithPagination")
fun <T> List<T>.createPaginationResult(
    pagination: Pagination,
    commonObjectsNumber: Long
) = PaginationResult(
    pagination.page,
    calculatePagesNumber(
        commonObjectsNumber,
        pagination.size
    ),
    this,
    pagination.size
)

@JsExport
@JsName("createPaginationResultWithFirstIndex")
fun <T> List<T>.createPaginationResult(
    firstIndex: Int,
    commonObjectsNumber: Long
) = PaginationResult(
    calculatePage(firstIndex, size),
    calculatePagesNumber(
        commonObjectsNumber,
        size
    ),
    this,
    size
)

@JsExport
@JsName("createPaginationResultWithPair")
fun <T> Pair<Long, List<T>>.createPaginationResult(
    pagination: Pagination
) = second.createPaginationResult(pagination, first)
