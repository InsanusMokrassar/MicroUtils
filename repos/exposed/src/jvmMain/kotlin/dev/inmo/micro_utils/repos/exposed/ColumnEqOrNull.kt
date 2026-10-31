package dev.inmo.micro_utils.repos.exposed

import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.isNotNull
import org.jetbrains.exposed.v1.core.isNull
import org.jetbrains.exposed.v1.core.neq

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
