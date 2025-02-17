package dev.inmo.micro_utils.pagination

import org.jetbrains.exposed.sql.*

fun Query.paginate(with: Pagination, orderBy: Pair<Expression<*>, SortOrder>? = null) =
    limit(with.size)
    .offset(with.firstIndex.toLong())
    .let {
        if (orderBy != null) {
            it.orderBy(
                orderBy.first,
                orderBy.second
            )
        } else {
            it
        }
    }

fun Query.paginate(with: Pagination, orderBy: Expression<*>?, reversed: Boolean = false) = paginate(
    with,
    orderBy ?.let { it to if (reversed) SortOrder.DESC else SortOrder.ASC }
)

