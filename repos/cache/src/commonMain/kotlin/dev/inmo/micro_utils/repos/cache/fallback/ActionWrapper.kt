package dev.inmo.micro_utils.repos.cache.fallback

import dev.inmo.micro_utils.coroutines.runCatchingSafely
import kotlinx.coroutines.withTimeout

interface ActionWrapper {
    suspend fun <T> wrap(block: suspend () -> T): Result<T>

    class Timeouted(private val timeoutMillis: Long) : ActionWrapper {
        override suspend fun <T> wrap(block: suspend () -> T): Result<T> = runCatchingSafely {
            withTimeout(timeoutMillis) {
                block()
            }
        }
    }
    object Direct : ActionWrapper {
        override suspend fun <T> wrap(block: suspend () -> T): Result<T> = runCatchingSafely { block() }
    }
}
