package dev.inmo.micro_utils.repos.pagination

import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.repos.*
import kotlin.js.JsExport

@JsExport
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

@JsExport
suspend inline fun <Key, Value, REPO : ReadOneToManyKeyValueRepo<Key, Value>> REPO.doForAll(
    block: (List<Pair<Key, List<Value>>>) -> Unit
) = doForAll({ keys(it, false) }, block)

@JsExport
suspend inline fun <Key, Value, REPO : ReadOneToManyKeyValueRepo<Key, Value>> REPO.getAll(
    @Suppress("REDUNDANT_INLINE_SUSPEND_FUNCTION_TYPE")
    methodCaller: suspend REPO.(Pagination) -> PaginationResult<Key>
): List<Pair<Key, List<Value>>> {
    val resultList = mutableListOf<Pair<Key, List<Value>>>()
    doForAll(methodCaller) {
        resultList.addAll(it)
    }
    return resultList
}
