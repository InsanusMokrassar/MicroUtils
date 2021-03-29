package dev.inmo.micro_utils.pagination.utils

import dev.inmo.micro_utils.pagination.*

suspend fun <T> getAll(
    initialPagination: Pagination = FirstPagePagination(),
    paginationMapper: (PaginationResult<T>) -> Pagination?,
    block: suspend (Pagination) -> PaginationResult<T>
): List<T> {
    val results = mutableListOf<T>()
    doForAll(initialPagination, paginationMapper) {
        block(it).also {
            results.addAll(it.results)
        }
    }
    return results.toList()
}

suspend fun <T> getAllWithNextPaging(
    initialPagination: Pagination = FirstPagePagination(),
    block: suspend (Pagination) -> PaginationResult<T>
): List<T> = getAll(
    initialPagination,
    { it.nextPageIfNotEmpty() },
    block
)

suspend fun <T> getAllWithCurrentPaging(
    initialPagination: Pagination = FirstPagePagination(),
    block: suspend (Pagination) -> PaginationResult<T>
): List<T> = getAll(
    initialPagination,
    { it.currentPageIfNotEmpty() },
    block
)
