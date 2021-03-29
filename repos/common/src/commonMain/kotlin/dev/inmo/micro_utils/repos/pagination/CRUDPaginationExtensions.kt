package dev.inmo.micro_utils.repos.pagination

import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.pagination.utils.doForAllWithNextPaging
import dev.inmo.micro_utils.pagination.utils.getAllWithNextPaging
import dev.inmo.micro_utils.repos.ReadStandardCRUDRepo

@Deprecated("Will be removed soon due to redundancy. Can be replaced with other doForAll extensions")
suspend inline fun <T, ID, REPO : ReadStandardCRUDRepo<T, ID>> REPO.doForAll(
    @Suppress("REDUNDANT_INLINE_SUSPEND_FUNCTION_TYPE")
    crossinline methodCaller: suspend REPO.(Pagination) -> PaginationResult<T>,
    crossinline block: (List<T>) -> Unit
) {
    doForAllWithNextPaging {
        methodCaller(it).also {
            block(it.results)
        }
    }
}

@Deprecated("Will be removed soon due to redundancy. Can be replaced with other doForAll extensions")
suspend inline fun <T, ID, REPO : ReadStandardCRUDRepo<T, ID>> REPO.doForAll(
    crossinline block: (List<T>) -> Unit
) = doForAllWithNextPaging {
    getByPagination(it).also { block(it.results) }
}

suspend inline fun <T, ID, REPO : ReadStandardCRUDRepo<T, ID>> REPO.getAll(
    @Suppress("REDUNDANT_INLINE_SUSPEND_FUNCTION_TYPE")
    crossinline methodCaller: suspend REPO.(Pagination) -> PaginationResult<T>
): List<T> = getAllWithNextPaging {
    methodCaller(this, it)
}
