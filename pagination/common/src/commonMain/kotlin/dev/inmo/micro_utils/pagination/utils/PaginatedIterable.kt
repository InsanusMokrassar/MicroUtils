package dev.inmo.micro_utils.pagination.utils

import dev.inmo.micro_utils.pagination.*

/**
 * An iterator that lazily fetches items from a paginated data source.
 * It automatically fetches the next page when the current page is exhausted.
 *
 * @param T The type of items being iterated
 * @param pageSize The size of each page to fetch
 * @param countGetter A function that returns the total count of available items
 * @param paginationResultGetter A function that fetches a page of results for a given pagination
 */
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

/**
 * An iterable that lazily fetches items from a paginated data source.
 * It creates a [PaginatedIterator] that automatically fetches pages as needed.
 *
 * @param T The type of items being iterated
 * @param pageSize The size of each page to fetch
 * @param countGetter A function that returns the total count of available items
 * @param paginationResultGetter A function that fetches a page of results for a given pagination
 */
class PaginatedIterable<T>(
    private val pageSize: Int,
    private val countGetter: () -> Long,
    private val paginationResultGetter: Pagination.() -> PaginationResult<T>
) : Iterable<T> {
    override fun iterator(): Iterator<T> = PaginatedIterator(pageSize, countGetter, paginationResultGetter)
}

/**
 * Creates an [Iterable] that lazily fetches items from a paginated data source.
 * This is useful for iterating over large datasets without loading all items into memory at once.
 *
 * @param T The type of items being iterated
 * @param countGetter A function that returns the total count of available items
 * @param pageSize The size of each page to fetch. Defaults to [defaultPaginationPageSize]
 * @param paginationResultGetter A function that fetches a page of results for a given pagination
 * @return An [Iterable] that can be used in for-loops or other iterable contexts
 */
@Suppress("NOTHING_TO_INLINE")
inline fun <T> makeIterable(
    noinline countGetter: () -> Long,
    pageSize: Int = defaultPaginationPageSize,
    noinline paginationResultGetter: Pagination.() -> PaginationResult<T>
): Iterable<T> = PaginatedIterable(pageSize, countGetter, paginationResultGetter)
