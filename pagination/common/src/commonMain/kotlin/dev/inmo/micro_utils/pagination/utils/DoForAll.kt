package dev.inmo.micro_utils.pagination.utils

import dev.inmo.micro_utils.pagination.*

suspend fun <T> doForAll(
    initialPagination: Pagination = FirstPagePagination(),
    paginationMapper: (PaginationResult<T>) -> Pagination?,
    block: suspend (Pagination) -> PaginationResult<T>
) {
    doWithPagination(initialPagination) {
        block(it).let(paginationMapper)
    }
}

suspend fun <T> doForAllWithNextPaging(
    initialPagination: Pagination = FirstPagePagination(),
    block: suspend (Pagination) -> PaginationResult<T>
) {
    doForAll(
        initialPagination,
        { it.nextPageIfNotEmpty() },
        block
    )
}

suspend fun <T> doAllWithCurrentPaging(
    initialPagination: Pagination = FirstPagePagination(),
    block: suspend (Pagination) -> PaginationResult<T>
) {
    doForAll(
        initialPagination,
        { it.thisPageIfNotEmpty() },
        block
    )
}
