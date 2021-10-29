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

suspend fun <T, R> R.getAllBy(
    initialPagination: Pagination = FirstPagePagination(),
    paginationMapper: R.(PaginationResult<T>) -> Pagination?,
    block: suspend R.(Pagination) -> PaginationResult<T>
): List<T> = getAll(
    initialPagination,
    { paginationMapper(it) },
    { block(it) }
)

suspend fun <T> getAllWithNextPaging(
    initialPagination: Pagination = FirstPagePagination(),
    block: suspend (Pagination) -> PaginationResult<T>
): List<T> = getAll(
    initialPagination,
    { it.nextPageIfNotEmpty() },
    block
)

suspend fun <T, R> R.getAllByWithNextPaging(
    initialPagination: Pagination = FirstPagePagination(),
    block: suspend R.(Pagination) -> PaginationResult<T>
): List<T> = getAllWithNextPaging(
    initialPagination,
    { block(it) }
)

suspend fun <T> getAllWithCurrentPaging(
    initialPagination: Pagination = FirstPagePagination(),
    block: suspend (Pagination) -> PaginationResult<T>
): List<T> = getAll(
    initialPagination,
    { it.currentPageIfNotEmpty() },
    block
)

suspend fun <T, R> R.getAllByWithCurrentPaging(
    initialPagination: Pagination = FirstPagePagination(),
    block: suspend R.(Pagination) -> PaginationResult<T>
): List<T> = getAllWithCurrentPaging(
    initialPagination,
    { block(it) }
)
