package dev.inmo.micro_utils.repos.pagination

import dev.inmo.micro_utils.common.toCoercedInt
import dev.inmo.micro_utils.pagination.FirstPagePagination
import dev.inmo.micro_utils.repos.ReadCRUDRepo
import dev.inmo.micro_utils.repos.ReadKeyValueRepo
import dev.inmo.micro_utils.repos.ReadKeyValuesRepo

/**
 * Creates a pagination starting from the first page with size equal to the total count of items in this [ReadCRUDRepo].
 * This effectively creates a single page containing all items.
 *
 * @return A [FirstPagePagination] with size equal to the repository count
 */
suspend inline fun ReadCRUDRepo<*, *>.maxPagePagination() = FirstPagePagination(count().toCoercedInt())

/**
 * Creates a pagination starting from the first page with size equal to the total count of items in this [ReadKeyValueRepo].
 * This effectively creates a single page containing all items.
 *
 * @return A [FirstPagePagination] with size equal to the repository count
 */
suspend inline fun ReadKeyValueRepo<*, *>.maxPagePagination() = FirstPagePagination(count().toCoercedInt())

/**
 * Creates a pagination starting from the first page with size equal to the total count of items in this [ReadKeyValuesRepo].
 * This effectively creates a single page containing all items.
 *
 * @return A [FirstPagePagination] with size equal to the repository count
 */
suspend inline fun ReadKeyValuesRepo<*, *>.maxPagePagination() = FirstPagePagination(count().toCoercedInt())
