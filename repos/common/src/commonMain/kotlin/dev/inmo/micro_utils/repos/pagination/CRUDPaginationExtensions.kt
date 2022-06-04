package dev.inmo.micro_utils.repos.pagination

import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.pagination.utils.getAllWithNextPaging
import dev.inmo.micro_utils.repos.ReadCRUDRepo

suspend inline fun <T, ID, REPO : ReadCRUDRepo<T, ID>> REPO.getAll(
    @Suppress("REDUNDANT_INLINE_SUSPEND_FUNCTION_TYPE")
    crossinline methodCaller: suspend REPO.(Pagination) -> PaginationResult<T>
): List<T> = getAllWithNextPaging {
    methodCaller(this, it)
}
