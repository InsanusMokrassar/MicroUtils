package dev.inmo.micro_utils.repos.pagination

import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.repos.ReadStandardKeyValueRepo

suspend inline fun <Key, Value, REPO : ReadStandardKeyValueRepo<Key, Value>> REPO.doForAll(
    @Suppress("REDUNDANT_INLINE_SUSPEND_FUNCTION_TYPE")
    methodCaller: suspend REPO.(Pagination) -> PaginationResult<Key>,
    block: (List<Pair<Key, Value>>) -> Unit
) {
    doWithPagination {
        methodCaller(it).also { keys ->
            block(keys.results.mapNotNull { key -> get(key) ?.let { value -> key to value } })
        }.nextPageIfNotEmpty()
    }
}

suspend inline fun <Key, Value, REPO : ReadStandardKeyValueRepo<Key, Value>> REPO.doForAll(
    block: (List<Pair<Key, Value>>) -> Unit
) = doForAll({ keys(it, false) }, block)

suspend inline fun <Key, Value, REPO : ReadStandardKeyValueRepo<Key, Value>> REPO.getAll(
    @Suppress("REDUNDANT_INLINE_SUSPEND_FUNCTION_TYPE")
    methodCaller: suspend REPO.(Pagination) -> PaginationResult<Key>
): List<Pair<Key, Value>> {
    val resultList = mutableListOf<Pair<Key, Value>>()
    doForAll(methodCaller) {
        resultList.addAll(it)
    }
    return resultList
}
