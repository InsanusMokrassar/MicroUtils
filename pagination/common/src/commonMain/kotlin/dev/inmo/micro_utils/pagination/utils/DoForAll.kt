package dev.inmo.micro_utils.pagination.utils

import dev.inmo.micro_utils.pagination.*

/**
 * Executes [block] for each page in a paginated sequence.
 * The [paginationMapper] determines the next pagination to use based on the current result.
 * Stops when [paginationMapper] returns null.
 *
 * @param T The type of items in each page
 * @param initialPagination The pagination to start with. Defaults to [FirstPagePagination]
 * @param paginationMapper Function that determines the next pagination based on the current result. Return null to stop
 * @param block Function that processes each page and returns a [PaginationResult]
 */
inline fun <T> doForAll(
    initialPagination: Pagination = FirstPagePagination(),
    paginationMapper: (PaginationResult<T>) -> Pagination?,
    block: (Pagination) -> PaginationResult<T>
) {
    doWithPagination(initialPagination) {
        block(it).let(paginationMapper)
    }
}

/**
 * Executes [block] for each page in a paginated sequence, automatically moving to the next page
 * until an empty page or the last page is reached.
 *
 * @param T The type of items in each page
 * @param initialPagination The pagination to start with. Defaults to [FirstPagePagination]
 * @param block Function that processes each page and returns a [PaginationResult]
 */
inline fun <T> doForAllWithNextPaging(
    initialPagination: Pagination = FirstPagePagination(),
    block: (Pagination) -> PaginationResult<T>
) {
    doForAll(
        initialPagination,
        { it.nextPageIfNotEmptyOrLastPage() },
        block
    )
}

/**
 * Executes [block] for each page in a paginated sequence, automatically moving to the next page
 * until an empty page or the last page is reached. Uses current page pagination logic.
 *
 * @param T The type of items in each page
 * @param initialPagination The pagination to start with. Defaults to [FirstPagePagination]
 * @param block Function that processes each page and returns a [PaginationResult]
 */
inline fun <T> doAllWithCurrentPaging(
    initialPagination: Pagination = FirstPagePagination(),
    block: (Pagination) -> PaginationResult<T>
) {
    doForAll(
        initialPagination,
        { it.nextPageIfNotEmptyOrLastPage() },
        block
    )
}

/**
 * Alias for [doAllWithCurrentPaging]. Executes [block] for each page in a paginated sequence.
 *
 * @param T The type of items in each page
 * @param initialPagination The pagination to start with. Defaults to [FirstPagePagination]
 * @param block Function that processes each page and returns a [PaginationResult]
 */
inline fun <T> doForAllWithCurrentPaging(
    initialPagination: Pagination = FirstPagePagination(),
    block: (Pagination) -> PaginationResult<T>
) = doAllWithCurrentPaging(initialPagination, block)
