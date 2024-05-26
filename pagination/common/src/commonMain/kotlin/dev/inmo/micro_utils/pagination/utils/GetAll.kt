package dev.inmo.micro_utils.pagination.utils

import dev.inmo.micro_utils.pagination.*

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

inline fun <T, R> R.getAllBy(
    initialPagination: Pagination = FirstPagePagination(),
    paginationMapper: R.(PaginationResult<T>) -> Pagination?,
    block: R.(Pagination) -> PaginationResult<T>
): List<T> = getAll(
    initialPagination,
    { paginationMapper(it) },
    { block(it) }
)

inline fun <T> getAllWithNextPaging(
    initialPagination: Pagination = FirstPagePagination(),
    block: (Pagination) -> PaginationResult<T>
): List<T> = getAll(
    initialPagination,
    { it.nextPageIfNotEmptyOrLastPage() },
    block
)

inline fun <T, R> R.getAllByWithNextPaging(
    initialPagination: Pagination = FirstPagePagination(),
    block: R.(Pagination) -> PaginationResult<T>
): List<T> = getAllWithNextPaging(
    initialPagination,
    { block(it) }
)

inline fun <T> getAllWithCurrentPaging(
    initialPagination: Pagination = FirstPagePagination(),
    block: (Pagination) -> PaginationResult<T>
): List<T> = getAll(
    initialPagination,
    { it.thisPageIfNotEmptyOrLastPage() },
    block
)

inline fun <T, R> R.getAllByWithCurrentPaging(
    initialPagination: Pagination = FirstPagePagination(),
    block: R.(Pagination) -> PaginationResult<T>
): List<T> = getAllWithCurrentPaging(
    initialPagination
) { block(it) }
