package dev.inmo.micro_utils.pagination

import kotlinx.serialization.*
import kotlin.math.ceil

/**
 * @param page Current page number
 * @param size Current page size. It can be greater than size of [results]
 * @param results Result objects
 * @param objectsNumber Count of all objects across all pages
 */
@Serializable
data class PaginationResult<T>(
    override val page: Int,
    override val size: Int,
    val results: List<T>,
    val objectsNumber: Long
) : Pagination {
    /**
     * Amount of pages for current pagination
     */
    @EncodeDefault
    @SerialName("pagesNumber")
    val pagesNumberLong: Long = ceil(objectsNumber / size.toFloat()).toLong()
    /**
     * Amount of pages for current pagination
     */
    @Transient
    val pagesNumber: Int = pagesNumberLong.toInt()

    constructor(
        page: Int,
        results: List<T>,
        pagesNumber: Int,
        size: Int = results.size
    ) : this(
        page,
        size,
        results,
        (pagesNumber * size).toLong()
    )
}

val PaginationResult<*>.lastPageLong
    get() = pagesNumberLong - 1

val PaginationResult<*>.lastPage
    get() = lastPageLong.toInt()

val PaginationResult<*>.isLastPage
    get() = page.toLong() == lastPageLong

fun <T> emptyPaginationResult(
    basePagination: Pagination,
    objectsNumber: Number
) = PaginationResult<T>(
    basePagination.page,
    basePagination.size,
    emptyList(),
    objectsNumber.toLong()
)
fun <T> emptyPaginationResult(
    basePagination: Pagination,
) = emptyPaginationResult<T>(basePagination, 0)
fun <T> emptyPaginationResult() = emptyPaginationResult<T>(FirstPagePagination(0))

/**
 * @return New [PaginationResult] with [data] without checking of data sizes equality
 */
inline fun <I, O> PaginationResult<I>.changeResultsUnchecked(
    block: PaginationResult<I>.() -> List<O>
): PaginationResult<O> = PaginationResult(page, size, block(), objectsNumber)

/**
 * @return New [PaginationResult] with [data] without checking of data sizes equality
 */
fun <I, O> PaginationResult<I>.changeResultsUnchecked(
    data: List<O>
): PaginationResult<O> = changeResultsUnchecked { data }
/**
 * @return New [PaginationResult] with [data] <b>with</b> checking of data sizes equality
 */
inline fun <I, O> PaginationResult<I>.changeResults(
    block: PaginationResult<I>.() -> List<O>
): PaginationResult<O> {
    val data = block()
    require(data.size == results.size)
    return changeResultsUnchecked(data)
}
/**
 * @return New [PaginationResult] with [data] <b>with</b> checking of data sizes equality
 */
fun <I, O> PaginationResult<I>.changeResults(
    data: List<O>
): PaginationResult<O> = changeResults { data }

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
