package dev.inmo.micro_utils.safe_wrapper

interface SafeWrapper<T> {
    fun <R> safe (block: T.() -> R): Result<R> = unsafeTarget().runCatching(block)
    fun <R> unsafe(block: T.() -> R): R = unsafeTarget().block()
    fun unsafeTarget(): T

    class Default<T>(private val t: T) : SafeWrapper<T> { override fun unsafeTarget(): T = t }

    companion object {
        operator fun <T> invoke(t: T) = Default(t)
    }
}
