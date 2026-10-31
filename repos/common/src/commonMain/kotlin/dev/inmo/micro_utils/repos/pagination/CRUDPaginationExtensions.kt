package dev.inmo.micro_utils.repos.pagination

import dev.inmo.micro_utils.common.toCoercedInt
import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.pagination.utils.getAllWithNextPaging
import dev.inmo.micro_utils.repos.ReadCRUDRepo

/**
 * Retrieves all items from a [ReadCRUDRepo] by iterating through pages starting from the given [pagination].
 * Uses the provided [methodCaller] to fetch each page.
 *
 * @param T The type of objects in the repository
 * @param ID The type of IDs in the repository
 * @param REPO The specific repository type
 * @param pagination The starting pagination parameters
 * @param methodCaller A function that fetches a page of results from the repository
 * @return A list of all items across all pages
 */
suspend inline fun <T, ID, REPO : ReadCRUDRepo<T, ID>> REPO.getAll(
    pagination: Pagination,
    @Suppress("REDUNDANT_INLINE_SUSPEND_FUNCTION_TYPE")
    crossinline methodCaller: suspend REPO.(Pagination) -> PaginationResult<T>
): List<T> = getAllWithNextPaging(pagination) {
    methodCaller(this, it)
}

/**
 * Retrieves all items from a [ReadCRUDRepo] by iterating through all pages.
 * Uses [maxPagePagination] to determine the starting pagination and the provided [methodCaller] to fetch each page.
 *
 * @param T The type of objects in the repository
 * @param ID The type of IDs in the repository
 * @param REPO The specific repository type
 * @param methodCaller A function that fetches a page of results from the repository
 * @return A list of all items across all pages
 */
suspend inline fun <T, ID, REPO : ReadCRUDRepo<T, ID>> REPO.getAll(
    @Suppress("REDUNDANT_INLINE_SUSPEND_FUNCTION_TYPE")
    crossinline methodCaller: suspend REPO.(Pagination) -> PaginationResult<T>
): List<T> = getAll(maxPagePagination(), methodCaller)
