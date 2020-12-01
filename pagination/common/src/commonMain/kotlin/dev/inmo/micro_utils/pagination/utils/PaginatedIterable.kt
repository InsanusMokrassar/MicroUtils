package dev.inmo.micro_utils.pagination.utils

import dev.inmo.micro_utils.pagination.*

class PaginatedIterator<T>(
    pageSize: Int,
    private val countGetter: () -> Long,
    private val paginationResultGetter: Pagination.() -> PaginationResult<T>
) : Iterator<T> {
    private var pagination = FirstPagePagination(pageSize)
    private val currentStack = mutableListOf<T>()
    override fun hasNext(): Boolean = currentStack.isNotEmpty() || (countGetter() < pagination.lastIndexExclusive)

    override fun next(): T {
        if (currentStack.isEmpty()) {
            val resultPagination = paginationResultGetter.invoke(pagination)
            currentStack.addAll(resultPagination.results)
            require(currentStack.isNotEmpty()) { "There is no elements left" }
            pagination = resultPagination.nextPage()
        }
        return currentStack.removeFirst()
    }
}

class PaginatedIterable<T>(
    private val pageSize: Int,
    private val countGetter: () -> Long,
    private val paginationResultGetter: Pagination.() -> PaginationResult<T>
) : Iterable<T> {
    override fun iterator(): Iterator<T> = PaginatedIterator(pageSize, countGetter, paginationResultGetter)
}

/**
 * Will make iterable using incoming [countGetter] and [paginationResultGetter]
 */
@Suppress("NOTHING_TO_INLINE")
inline fun <T> makeIterable(
    noinline countGetter: () -> Long,
    pageSize: Int = defaultMediumPageSize,
    noinline paginationResultGetter: Pagination.() -> PaginationResult<T>
): Iterable<T> = PaginatedIterable(pageSize, countGetter, paginationResultGetter)
