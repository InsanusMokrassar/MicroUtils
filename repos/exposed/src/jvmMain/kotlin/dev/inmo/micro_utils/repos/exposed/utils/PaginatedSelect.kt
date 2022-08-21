package dev.inmo.micro_utils.repos.exposed.utils

import dev.inmo.micro_utils.pagination.*
import org.jetbrains.exposed.sql.*

fun <T> Query.selectPaginated(
    pagination: Pagination,
    orderBy: Pair<Expression<*>, SortOrder>? = null,
    createResult: (ResultRow) -> T
): PaginationResult<T> {
    val count = count()
    val list = paginate(pagination, orderBy).map(createResult)
    return list.createPaginationResult(pagination, count)
}

fun <T> Query.selectPaginated(
    pagination: Pagination,
    orderBy: Expression<*>?,
    reversed: Boolean = false,
    createResult: (ResultRow) -> T
) = selectPaginated(pagination, orderBy ?.let { it to if (reversed) SortOrder.DESC else SortOrder.ASC }, createResult)
