package dev.inmo.micro_utils.repos.pagination

import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.pagination.utils.getAllWithNextPaging
import dev.inmo.micro_utils.repos.ReadStandardKeyValueRepo

@Deprecated("Will be removed soon due to redundancy")
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

@Deprecated("Will be removed soon due to redundancy")
suspend inline fun <Key, Value, REPO : ReadStandardKeyValueRepo<Key, Value>> REPO.doForAll(
    block: (List<Pair<Key, Value>>) -> Unit
) = doForAll({ keys(it, false) }, block)

suspend inline fun <Key, Value, REPO : ReadStandardKeyValueRepo<Key, Value>> REPO.getAll(
    @Suppress("REDUNDANT_INLINE_SUSPEND_FUNCTION_TYPE")
    crossinline methodCaller: suspend REPO.(Pagination) -> PaginationResult<Key>
): List<Pair<Key, Value>> = getAllWithNextPaging {
    val result = methodCaller(it)
    result.changeResultsUnchecked(
        result.results.mapNotNull { it to (get(it) ?: return@mapNotNull null) }
    )
}
