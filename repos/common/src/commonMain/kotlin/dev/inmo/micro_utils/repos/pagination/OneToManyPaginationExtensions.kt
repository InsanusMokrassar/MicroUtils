package dev.inmo.micro_utils.repos.pagination

import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.repos.ReadOneToManyKeyValueRepo

@Deprecated("Will be removed soon due to redundancy")
suspend inline fun <Key, Value, REPO : ReadOneToManyKeyValueRepo<Key, Value>> REPO.doForAll(
    @Suppress("REDUNDANT_INLINE_SUSPEND_FUNCTION_TYPE")
    methodCaller: suspend REPO.(Pagination) -> PaginationResult<Key>,
    block: (List<Pair<Key, List<Value>>>) -> Unit
) {
    doWithPagination {
        methodCaller(it).also { keys ->
            block(
                keys.results.mapNotNull { key ->
                    val values = mutableListOf<Value>()
                    doWithPagination {
                        get(key, it).also {
                            values.addAll(it.results)
                        }.nextPageIfNotEmpty()
                    }
                    key to values
                }
            )
        }.nextPageIfNotEmpty()
    }
}

@Deprecated("Will be removed soon due to redundancy")
suspend inline fun <Key, Value, REPO : ReadOneToManyKeyValueRepo<Key, Value>> REPO.doForAll(
    block: (List<Pair<Key, List<Value>>>) -> Unit
) = doForAll({ keys(it, false) }, block)

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
