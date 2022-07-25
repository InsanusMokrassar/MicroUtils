package dev.inmo.micro_utils.pagination

import kotlinx.serialization.Serializable

@Serializable
data class PaginationResult<T>(
    override val page: Int,
    val pagesNumber: Int,
    val results: List<T>,
    override val size: Int,
    val objectsCount: Long = pagesNumber * size.toLong()
) : Pagination

fun <T> emptyPaginationResult() = PaginationResult<T>(0, 0, emptyList(), 0)

/**
 * @return New [PaginationResult] with [data] without checking of data sizes equality
 */
fun <I, O> PaginationResult<I>.changeResultsUnchecked(
    data: List<O>
): PaginationResult<O> = PaginationResult(page, pagesNumber, data, size)
/**
 * @return New [PaginationResult] with [data] <b>with</b> checking of data sizes equality
 */
fun <I, O> PaginationResult<I>.changeResults(
    data: List<O>
): PaginationResult<O> {
    require(data.size == results.size)
    return changeResultsUnchecked(data)
}

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
    pagination.size,
    commonObjectsNumber
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
    size,
    commonObjectsNumber
)

fun <T> Pair<Long, List<T>>.createPaginationResult(
    pagination: Pagination
) = second.createPaginationResult(pagination, first)
