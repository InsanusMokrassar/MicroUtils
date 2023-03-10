package dev.inmo.micro_utils.repos.pagination

import dev.inmo.micro_utils.common.toCoercedInt
import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.pagination.utils.getAllWithNextPaging
import dev.inmo.micro_utils.repos.ReadKeyValueRepo

suspend inline fun <Key, Value, REPO : ReadKeyValueRepo<Key, Value>> REPO.getAll(
    pagination: Pagination,
    @Suppress("REDUNDANT_INLINE_SUSPEND_FUNCTION_TYPE")
    crossinline methodCaller: suspend REPO.(Pagination) -> PaginationResult<Key>
): List<Pair<Key, Value>> = getAllWithNextPaging(pagination) {
    val result = methodCaller(it)
    result.changeResultsUnchecked(
        result.results.mapNotNull { it to (get(it) ?: return@mapNotNull null) }
    )
}

suspend inline fun <Key, Value, REPO : ReadKeyValueRepo<Key, Value>> REPO.getAll(
    @Suppress("REDUNDANT_INLINE_SUSPEND_FUNCTION_TYPE")
    crossinline methodCaller: suspend REPO.(Pagination) -> PaginationResult<Key>
): List<Pair<Key, Value>> = getAll(maxPagePagination(), methodCaller)
