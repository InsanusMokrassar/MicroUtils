package dev.inmo.micro_utils.repos.exposed

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.isNotNull
import org.jetbrains.exposed.sql.SqlExpressionBuilder.isNull
import org.jetbrains.exposed.sql.SqlExpressionBuilder.neq

fun <T> Column<T?>.eqOrIsNull(
    value: T?
) = if (value == null) {
    isNull()
} else {
    eq(value)
}

fun <T> Column<T?>.neqOrIsNotNull(
    value: T?
) = if (value == null) {
    isNotNull()
} else {
    neq(value)
}
