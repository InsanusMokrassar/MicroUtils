package dev.inmo.micro_utils.pagination

import kotlinx.serialization.Serializable

@Serializable
data class PaginationResult<T>(
    override val page: Int,
    val pagesNumber: Int,
    val results: List<T>,
    override val size: Int
) : Pagination

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

fun <T> Pair<Long, List<T>>.createPaginationResult(
    pagination: Pagination
) = second.createPaginationResult(pagination, first)
