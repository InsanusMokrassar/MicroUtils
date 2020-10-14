package dev.inmo.micro_utils.repos.pagination

import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.repos.ReadStandardCRUDRepo
import kotlin.js.JsExport

@JsExport
suspend inline fun <T, ID, REPO : ReadStandardCRUDRepo<T, ID>> REPO.doForAll(
    @Suppress("REDUNDANT_INLINE_SUSPEND_FUNCTION_TYPE")
    methodCaller: suspend REPO.(Pagination) -> PaginationResult<T>,
    block: (List<T>) -> Unit
) {
    doWithPagination {
        methodCaller(it).also {
            block(it.results)
        }.nextPageIfNotEmpty()
    }
}

@JsExport
suspend inline fun <T, ID, REPO : ReadStandardCRUDRepo<T, ID>> REPO.doForAll(
    block: (List<T>) -> Unit
) = doForAll({ getByPagination(it) }, block)

@JsExport
suspend inline fun <T, ID, REPO : ReadStandardCRUDRepo<T, ID>> REPO.getAll(
    @Suppress("REDUNDANT_INLINE_SUSPEND_FUNCTION_TYPE")
    methodCaller: suspend REPO.(Pagination) -> PaginationResult<T>
): List<T> {
    val resultList = mutableListOf<T>()
    doForAll(methodCaller) {
        resultList.addAll(it)
    }
    return resultList
}
