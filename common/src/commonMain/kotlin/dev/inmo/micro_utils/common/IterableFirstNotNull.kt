package dev.inmo.micro_utils.common

fun <T> Iterable<T?>.firstNotNull() = first { it != null }!!
