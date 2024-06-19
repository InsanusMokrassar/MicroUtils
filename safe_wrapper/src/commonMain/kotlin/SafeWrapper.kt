package dev.inmo.micro_utils.safe_wrapper

import dev.inmo.micro_utils.coroutines.runCatchingSafely

interface SafeWrapper<T> {
    fun <R> safe(block: T.() -> R): Result<R> = unsafeTarget().runCatching(block)
    fun <R> unsafe(block: T.() -> R): R = unsafeTarget().block()
    suspend fun <R> safeS(block: suspend T.() -> R): Result<R> = unsafeTarget().run {
        runCatchingSafely(block = { block() })
    }
    suspend fun <R> unsafeS(block: suspend T.() -> R): R = unsafeTarget().block()
    fun unsafeTarget(): T

    class Default<T>(private val t: T) : SafeWrapper<T> { override fun unsafeTarget(): T = t }

    companion object {
        operator fun <T> invoke(t: T) = Default(t)
    }
}
