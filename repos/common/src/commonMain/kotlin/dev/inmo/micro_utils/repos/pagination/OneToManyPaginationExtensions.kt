package dev.inmo.micro_utils.repos.pagination

import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.pagination.utils.getAllWithNextPaging
import dev.inmo.micro_utils.repos.ReadOneToManyKeyValueRepo

suspend inline fun <Key, Value, REPO : ReadOneToManyKeyValueRepo<Key, Value>> REPO.getAll(
    @Suppress("REDUNDANT_INLINE_SUSPEND_FUNCTION_TYPE")
    crossinline methodCaller: suspend REPO.(Pagination) -> PaginationResult<Key>
): List<Pair<Key, List<Value>>> = getAllWithNextPaging {
    val keysResult = methodCaller(it)
    keysResult.changeResultsUnchecked(
        keysResult.results.map { k ->
            k to getAll(k)
        }
    )
}
