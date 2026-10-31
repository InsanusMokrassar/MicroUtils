package dev.inmo.micro_utils.pagination

import org.jetbrains.exposed.v1.core.Expression
import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.jdbc.Query

/**
 * Applies pagination to this Exposed [Query].
 * Sets the limit and offset based on the pagination parameters, and optionally orders the results.
 *
 * @param with The pagination parameters to apply
 * @param orderBy Optional pair of expression and sort order to order the results by
 * @return The query with pagination and optional ordering applied
 */
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

/**
 * Applies pagination to this Exposed [Query] with optional ordering and reversal.
 * Sets the limit and offset based on the pagination parameters.
 *
 * @param with The pagination parameters to apply
 * @param orderBy Optional expression to order the results by
 * @param reversed If true, orders in descending order; otherwise ascending. Defaults to false
 * @return The query with pagination and optional ordering applied
 */
fun Query.paginate(with: Pagination, orderBy: Expression<*>?, reversed: Boolean = false) = paginate(
    with,
    orderBy ?.let { it to if (reversed) SortOrder.DESC else SortOrder.ASC }
)

