package dev.inmo.micro_utils.safe_wrapper

import dev.inmo.micro_utils.coroutines.runCatchingSafely

/**
 * A wrapper interface that provides safe and unsafe access to a target object.
 * Safe methods wrap operations in [Result] to catch and handle exceptions,
 * while unsafe methods execute directly and may throw exceptions.
 *
 * @param T The type of the wrapped target object
 */
interface SafeWrapper<T> {
    /**
     * Executes a synchronous block on the target, catching any exceptions and returning a [Result].
     *
     * @param R The return type of the block
     * @param block The operation to execute on the target
     * @return A [Result] containing either the successful result or the caught exception
     */
    fun <R> safe(block: T.() -> R): Result<R> = unsafeTarget().runCatching(block)
    
    /**
     * Executes a synchronous block on the target without exception handling.
     *
     * @param R The return type of the block
     * @param block The operation to execute on the target
     * @return The direct result of the block
     * @throws Throwable If the block throws an exception
     */
    fun <R> unsafe(block: T.() -> R): R = unsafeTarget().block()
    
    /**
     * Executes a suspending block on the target, catching any exceptions (except [kotlinx.coroutines.CancellationException])
     * and returning a [Result].
     *
     * @param R The return type of the block
     * @param block The suspending operation to execute on the target
     * @return A [Result] containing either the successful result or the caught exception
     */
    suspend fun <R> safeS(block: suspend T.() -> R): Result<R> = unsafeTarget().runCatchingSafely(block = block)
    
    /**
     * Executes a suspending block on the target without exception handling.
     *
     * @param R The return type of the block
     * @param block The suspending operation to execute on the target
     * @return The direct result of the block
     * @throws Throwable If the block throws an exception
     */
    suspend fun <R> unsafeS(block: suspend T.() -> R): R = unsafeTarget().block()
    
    /**
     * Provides access to the underlying wrapped target object.
     *
     * @return The target object
     */
    fun unsafeTarget(): T

    /**
     * Default implementation of [SafeWrapper] that wraps a provided target.
     *
     * @param T The type of the wrapped target
     */
    class Default<T>(private val t: T) : SafeWrapper<T> { 
        override fun unsafeTarget(): T = t 
    }

    companion object {
        /**
         * Creates a [SafeWrapper] instance wrapping the provided target.
         *
         * @param T The type of the target
         * @param t The target object to wrap
         * @return A [SafeWrapper.Default] instance
         */
        operator fun <T> invoke(t: T) = Default(t)
    }
}
