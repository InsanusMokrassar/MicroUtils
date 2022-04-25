package dev.inmo.micro_utils.handlers.common

fun interface Handler<T> {
    suspend fun check(data: T): Boolean = true
    suspend fun handle(data: T)
}
