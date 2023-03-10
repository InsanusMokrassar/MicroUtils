package dev.inmo.micro_utils.repos.pagination

import dev.inmo.micro_utils.common.toCoercedInt
import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.pagination.utils.getAllWithNextPaging
import dev.inmo.micro_utils.repos.ReadKeyValuesRepo

suspend inline fun <Key, Value, REPO : ReadKeyValuesRepo<Key, Value>> REPO.getAll(
    pagination: Pagination,
    @Suppress("REDUNDANT_INLINE_SUSPEND_FUNCTION_TYPE")
    crossinline methodCaller: suspend REPO.(Pagination) -> PaginationResult<Key>
): List<Pair<Key, List<Value>>> = getAllWithNextPaging(pagination) {
    val keysResult = methodCaller(it)
    keysResult.changeResultsUnchecked(
        keysResult.results.map { k ->
            k to getAll(k)
        }
    )
}

suspend inline fun <Key, Value, REPO : ReadKeyValuesRepo<Key, Value>> REPO.getAll(
    @Suppress("REDUNDANT_INLINE_SUSPEND_FUNCTION_TYPE")
    crossinline methodCaller: suspend REPO.(Pagination) -> PaginationResult<Key>
): List<Pair<Key, List<Value>>> = getAll(FirstPagePagination(count().toCoercedInt()), methodCaller)
