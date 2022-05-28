package dev.inmo.micro_utils.pagination.utils

import dev.inmo.micro_utils.pagination.*

inline fun <T> doForAll(
    initialPagination: Pagination = FirstPagePagination(),
    paginationMapper: (PaginationResult<T>) -> Pagination?,
    block: (Pagination) -> PaginationResult<T>
) {
    doWithPagination(initialPagination) {
        block(it).let(paginationMapper)
    }
}

inline fun <T> doForAllWithNextPaging(
    initialPagination: Pagination = FirstPagePagination(),
    block: (Pagination) -> PaginationResult<T>
) {
    doForAll(
        initialPagination,
        { it.nextPageIfNotEmpty() },
        block
    )
}

inline fun <T> doAllWithCurrentPaging(
    initialPagination: Pagination = FirstPagePagination(),
    block: (Pagination) -> PaginationResult<T>
) {
    doForAll(
        initialPagination,
        { it.currentPageIfNotEmpty() },
        block
    )
}

inline fun <T> doForAllWithCurrentPaging(
    initialPagination: Pagination = FirstPagePagination(),
    block: (Pagination) -> PaginationResult<T>
) = doAllWithCurrentPaging(initialPagination, block)
