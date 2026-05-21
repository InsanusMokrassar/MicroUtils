package dev.inmo.micro_utils.repos.pagination

import dev.inmo.micro_utils.common.toCoercedInt
import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.pagination.utils.getAllWithNextPaging
import dev.inmo.micro_utils.repos.ReadKeyValueRepo

/**
 * Retrieves all key-value pairs from a [ReadKeyValueRepo] by iterating pages starting from [pagination].
 * Uses [methodCaller] to fetch each page of keys, then resolves each key to its value via [ReadKeyValueRepo.get].
 *
 * @param Key The type of keys in the repository
 * @param Value The type of values in the repository
 * @param REPO The specific repository type
 * @param pagination The starting pagination parameters
 * @param methodCaller A function that fetches a page of keys from the repository
 * @return List of all key-value pairs across all pages; entries with null values are excluded
 */
suspend inline fun <Key, Value, REPO : ReadKeyValueRepo<Key, Value>> REPO.getAll(
    pagination: Pagination,
    @Suppress("REDUNDANT_INLINE_SUSPEND_FUNCTION_TYPE")
    crossinline methodCaller: suspend REPO.(Pagination) -> PaginationResult<Key>
): List<Pair<Key, Value>> = getAllWithNextPaging(pagination) {
    val result = methodCaller(it)
    result.changeResultsUnchecked(
        result.results.mapNotNull { it to (get(it) ?: return@mapNotNull null) }
    )
}

/**
 * Retrieves all key-value pairs from a [ReadKeyValueRepo] by iterating all pages.
 * Uses [maxPagePagination] as the starting pagination and [methodCaller] to fetch each page of keys.
 *
 * @param Key The type of keys in the repository
 * @param Value The type of values in the repository
 * @param REPO The specific repository type
 * @param methodCaller A function that fetches a page of keys from the repository
 * @return List of all key-value pairs across all pages; entries with null values are excluded
 */
suspend inline fun <Key, Value, REPO : ReadKeyValueRepo<Key, Value>> REPO.getAll(
    @Suppress("REDUNDANT_INLINE_SUSPEND_FUNCTION_TYPE")
    crossinline methodCaller: suspend REPO.(Pagination) -> PaginationResult<Key>
): List<Pair<Key, Value>> = getAll(maxPagePagination(), methodCaller)
