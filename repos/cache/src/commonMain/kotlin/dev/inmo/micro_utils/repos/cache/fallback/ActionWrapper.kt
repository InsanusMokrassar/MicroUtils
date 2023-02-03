package dev.inmo.micro_utils.repos.cache.fallback

import dev.inmo.micro_utils.coroutines.runCatchingSafely
import kotlinx.coroutines.withTimeout

/**
 * Realizations should [wrap] the work with some conditions like retries on exceptions, calling timeout, etc.
 * 
 * @see Timeouted
 * @see Direct
 */
interface ActionWrapper {
    /**
     * Should execute [block] to take the result [T], but may return failure in case when something went wrong.
     * This method should never throw any [Exception]
     */
    suspend fun <T> wrap(block: suspend () -> T): Result<T>

    /**
     * This type of [ActionWrapper]s will use [withTimeout]([timeoutMillis]) and if original call
     * will not return anything in that timeout just return [Result] with failure
     */
    class Timeouted(private val timeoutMillis: Long) : ActionWrapper {
        override suspend fun <T> wrap(block: suspend () -> T): Result<T> = runCatchingSafely {
            withTimeout(timeoutMillis) {
                block()
            }
        }
    }
    /**
     * It is passthrough variant of [ActionWrapper] which will just call incoming block with wrapping into [runCatchingSafely]
     */
    object Direct : ActionWrapper {

        override suspend fun <T> wrap(block: suspend () -> T): Result<T> = runCatchingSafely { block() }
    }
}
