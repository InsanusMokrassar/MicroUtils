package dev.inmo.micro_utils.pagination

import dev.inmo.micro_utils.common.intersect
import kotlin.math.ceil
import kotlin.math.floor

/**
 * Base interface of pagination
 *
 * If you want to request something, you should use [SimplePagination]. If you need to return some result including
 * pagination - [PaginationResult]
 */
interface Pagination : ClosedRange<Int> {
    /**
     * Started with 0.
     * Number of page inside of pagination. Offset can be calculated as [page] * [size]
     */
    val page: Int
    /**
     * Can be 0, but can't be < 0
     * Size of current page. Offset can be calculated as [page] * [size]
     */
    val size: Int

    override val start: Int
        get() = page * size
    override val endInclusive: Int
        get() = start + size - 1
}

fun Pagination.intersect(
    other: Pagination
): Pagination? = (this as ClosedRange<Int>).intersect(other as ClosedRange<Int>) ?.let {
    PaginationByIndexes(it.first, it.second)
}

/**
 * Logical shortcut for comparison that page is 0
 */
inline val Pagination.isFirstPage
    get() = page == 0

fun Pagination.firstPage() = if (isFirstPage) this else SimplePagination(0, size)

/**
 * First number in index of objects. It can be used as offset for databases or other data sources
 */
val Pagination.firstIndex: Int
    get() = start

/**
 * Last number in index of objects. In fact, one [Pagination] object represent data in next range:
 *
 * [[firstIndex], [lastIndex]]; That means, that for [Pagination] with [Pagination.size] == 10 and [Pagination.page] == 1
 * you will retrieve [Pagination.firstIndex] == 10 and [Pagination.lastIndex] == 19. Here [Pagination.lastIndexExclusive] == 20
 */
val Pagination.lastIndexExclusive: Int
    get() = endInclusive + 1

/**
 * Last number in index of objects. In fact, one [Pagination] object represent data in next range:
 *
 * [[firstIndex], [lastIndex]]; That means, that for [Pagination] with [Pagination.size] == 10 and [Pagination.page] == 1
 * you will retrieve [Pagination.firstIndex] == 10 and [Pagination.lastIndex] == 19.
 */
val Pagination.lastIndex: Int
    get() = endInclusive

/**
 * Calculates pages count for given [datasetSize]
 */
fun calculatePagesNumber(datasetSize: Long, pageSize: Int): Int {
    return ceil(datasetSize.toDouble() / pageSize).toInt()
}
/**
 * Calculates pages count for given [datasetSize]. As a fact, it is shortcut for [calculatePagesNumber]
 * @return calculated page number which can be correctly used in [PaginationResult] as [PaginationResult.page] value
 */
fun calculatePagesNumber(pageSize: Int, datasetSize: Long): Int = calculatePagesNumber(datasetSize, pageSize)
/**
 * Calculates pages count for given [datasetSize]
 */
fun calculatePagesNumber(datasetSize: Int, pageSize: Int): Int =
    calculatePagesNumber(
        datasetSize.toLong(),
        pageSize
    )

/**
 * @return calculated page number which can be correctly used in [PaginationResult] as [PaginationResult.page] value
 */
fun calculatePage(firstIndex: Int, resultsSize: Int): Int = if (resultsSize > 0) {
    floor(firstIndex.toFloat() / resultsSize).toInt()
} else {
    0
}
