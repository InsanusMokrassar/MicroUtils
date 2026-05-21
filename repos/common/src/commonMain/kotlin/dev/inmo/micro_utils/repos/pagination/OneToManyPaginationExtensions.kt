package dev.inmo.micro_utils.repos.pagination

import dev.inmo.micro_utils.common.toCoercedInt
import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.pagination.utils.getAllWithNextPaging
import dev.inmo.micro_utils.repos.ReadKeyValuesRepo

/**
 * Retrieves all key-to-list-of-values pairs from a [ReadKeyValuesRepo] by iterating pages starting from [pagination].
 * Uses [methodCaller] to fetch each page of keys, then resolves all values per key via [ReadKeyValuesRepo.getAll].
 *
 * @param Key The type of keys in the repository
 * @param Value The type of values associated with keys
 * @param REPO The specific repository type
 * @param pagination The starting pagination parameters
 * @param methodCaller A function that fetches a page of keys from the repository
 * @return List of key-to-list-of-values pairs across all pages
 */
suspend inline fun <Key, Value, REPO : ReadKeyValuesRepo<Key, Value>> REPO.getAll(
    pagination: Pagination,
    @Suppress("REDUNDANT_INLINE_SUSPEND_FUNCTION_TYPE")
    crossinline methodCaller: suspend REPO.(Pagination) -> PaginationResult<Key>
): List<Pair<Key, List<Value>>> = getAllWithNextPaging(pagination) {
    val keysResult = methodCaller(it)
    keysResult.changeResultsUnchecked(
        keysResult.results.map { k ->
            k to getAll(k)
        }
    )
}

/**
 * Retrieves all key-to-list-of-values pairs from a [ReadKeyValuesRepo] by iterating all pages.
 * Uses [maxPagePagination] as the starting pagination and [methodCaller] to fetch each page of keys.
 *
 * @param Key The type of keys in the repository
 * @param Value The type of values associated with keys
 * @param REPO The specific repository type
 * @param methodCaller A function that fetches a page of keys from the repository
 * @return List of key-to-list-of-values pairs across all pages
 */
suspend inline fun <Key, Value, REPO : ReadKeyValuesRepo<Key, Value>> REPO.getAll(
    @Suppress("REDUNDANT_INLINE_SUSPEND_FUNCTION_TYPE")
    crossinline methodCaller: suspend REPO.(Pagination) -> PaginationResult<Key>
): List<Pair<Key, List<Value>>> = getAll(maxPagePagination(), methodCaller)
