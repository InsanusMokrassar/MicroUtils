package dev.inmo.micro_utils.repos

import dev.inmo.micro_utils.pagination.Pagination
import dev.inmo.micro_utils.pagination.firstIndex

fun limitClause(size: Long, since: Long? = null) = "${since ?.let { "$it, " } ?: ""}$size"
fun limitClause(size: Int, since: Int? = null) = limitClause(size.toLong(), since ?.toLong())
fun Pagination.limitClause() = limitClause(size, firstIndex)
