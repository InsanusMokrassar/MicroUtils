package dev.inmo.micro_utils.pagination.utils

import dev.inmo.micro_utils.pagination.*

/**
 * Retrieves all items from a paginated source by repeatedly calling [block] with different pagination parameters.
 * The [paginationMapper] determines the next pagination to use based on the current result.
 *
 * @param T The type of items being retrieved
 * @param initialPagination The pagination to start with. Defaults to [FirstPagePagination]
 * @param paginationMapper Function that determines the next pagination based on the current result. Return null to stop
 * @param block Function that retrieves a page of results for the given pagination
 * @return A list containing all retrieved items from all pages
 */
inline fun <T> getAll(
    initialPagination: Pagination = FirstPagePagination(),
    paginationMapper: (PaginationResult<T>) -> Pagination?,
    block: (Pagination) -> PaginationResult<T>
): List<T> {
    val results = mutableListOf<T>()
    doForAll(initialPagination, paginationMapper) {
        block(it).also {
            results.addAll(it.results)
        }
    }
    return results.toList()
}

/**
 * Retrieves all items from a paginated source using a receiver context.
 * This is useful when the pagination logic depends on the receiver object's state.
 *
 * @param T The type of items being retrieved
 * @param R The receiver type
 * @param initialPagination The pagination to start with. Defaults to [FirstPagePagination]
 * @param paginationMapper Function that determines the next pagination based on the current result
 * @param block Function that retrieves a page of results for the given pagination using the receiver context
 * @return A list containing all retrieved items from all pages
 */
inline fun <T, R> R.getAllBy(
    initialPagination: Pagination = FirstPagePagination(),
    paginationMapper: R.(PaginationResult<T>) -> Pagination?,
    block: R.(Pagination) -> PaginationResult<T>
): List<T> = getAll(
    initialPagination,
    { paginationMapper(it) },
    { block(it) }
)

/**
 * Retrieves all items from a paginated source, automatically moving to the next page
 * until an empty page or the last page is reached.
 *
 * @param T The type of items being retrieved
 * @param initialPagination The pagination to start with. Defaults to [FirstPagePagination]
 * @param block Function that retrieves a page of results for the given pagination
 * @return A list containing all retrieved items from all pages
 */
inline fun <T> getAllWithNextPaging(
    initialPagination: Pagination = FirstPagePagination(),
    block: (Pagination) -> PaginationResult<T>
): List<T> = getAll(
    initialPagination,
    { it.nextPageIfNotEmptyOrLastPage() },
    block
)

/**
 * Retrieves all items from a paginated source using a receiver context,
 * automatically moving to the next page until an empty page or the last page is reached.
 *
 * @param T The type of items being retrieved
 * @param R The receiver type
 * @param initialPagination The pagination to start with. Defaults to [FirstPagePagination]
 * @param block Function that retrieves a page of results for the given pagination using the receiver context
 * @return A list containing all retrieved items from all pages
 */
inline fun <T, R> R.getAllByWithNextPaging(
    initialPagination: Pagination = FirstPagePagination(),
    block: R.(Pagination) -> PaginationResult<T>
): List<T> = getAllWithNextPaging(
    initialPagination,
    { block(it) }
)

/**
 * Retrieves all items from a paginated source, automatically moving to the next page
 * until an empty page or the last page is reached. Uses current page pagination logic.
 *
 * @param T The type of items being retrieved
 * @param initialPagination The pagination to start with. Defaults to [FirstPagePagination]
 * @param block Function that retrieves a page of results for the given pagination
 * @return A list containing all retrieved items from all pages
 */
inline fun <T> getAllWithCurrentPaging(
    initialPagination: Pagination = FirstPagePagination(),
    block: (Pagination) -> PaginationResult<T>
): List<T> = getAll(
    initialPagination,
    { it.thisPageIfNotEmptyOrLastPage() },
    block
)

/**
 * Retrieves all items from a paginated source using a receiver context,
 * automatically moving to the next page until an empty page or the last page is reached.
 * Uses current page pagination logic.
 *
 * @param T The type of items being retrieved
 * @param R The receiver type
 * @param initialPagination The pagination to start with. Defaults to [FirstPagePagination]
 * @param block Function that retrieves a page of results for the given pagination using the receiver context
 * @return A list containing all retrieved items from all pages
 */
inline fun <T, R> R.getAllByWithCurrentPaging(
    initialPagination: Pagination = FirstPagePagination(),
    block: R.(Pagination) -> PaginationResult<T>
): List<T> = getAllWithCurrentPaging(
    initialPagination
) { block(it) }
