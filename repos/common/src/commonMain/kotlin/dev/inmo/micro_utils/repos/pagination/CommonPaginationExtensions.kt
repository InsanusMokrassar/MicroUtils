package dev.inmo.micro_utils.repos.pagination

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
    { it.thisPageIfNotEmpty() },
    block
)
