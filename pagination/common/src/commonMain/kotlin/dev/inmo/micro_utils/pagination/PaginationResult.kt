package dev.inmo.micro_utils.pagination

import kotlinx.serialization.Serializable
import kotlin.math.ceil

@Serializable
data class PaginationResult<T>(
    override val page: Int,
    override val size: Int,
    val results: List<T>,
    val objectsCount: Long
) : Pagination {
    val pagesNumber: Int = ceil(objectsCount / size.toFloat()).toInt()

    constructor(
        page: Int,
        results: List<T>,
        pagesNumber: Int,
        size: Int
    ) : this(
        page,
        size,
        results,
        (pagesNumber * size).toLong()
    )
    @Deprecated("Replace with The other order of incoming parameters or objectsCount parameter")
    constructor(
        page: Int,
        pagesNumber: Int,
        results: List<T>,
        size: Int
    ) : this(
        page,
        results,
        pagesNumber,
        size
    )
}

fun <T> emptyPaginationResult() = PaginationResult<T>(0, 0, emptyList(), 0L)

/**
 * @return New [PaginationResult] with [data] without checking of data sizes equality
 */
fun <I, O> PaginationResult<I>.changeResultsUnchecked(
    data: List<O>
): PaginationResult<O> = PaginationResult(page, size, data, objectsCount)
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
    pagination.size,
    this,
    commonObjectsNumber
)

fun <T> List<T>.createPaginationResult(
    firstIndex: Int,
    commonObjectsNumber: Long
) = PaginationResult(
    calculatePage(firstIndex, size),
    size,
    this,
    commonObjectsNumber
)

fun <T> Pair<Long, List<T>>.createPaginationResult(
    pagination: Pagination
) = second.createPaginationResult(pagination, first)
