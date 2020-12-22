@file:Suppress("NOTHING_TO_INLINE")

package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

/**
 * Shortcut for chain if [Flow.onEach] and [Flow.launchIn]
 */
inline fun <T> Flow<T>.subscribe(scope: CoroutineScope, noinline block: suspend (T) -> Unit) = onEach(block).launchIn(scope)

/**
 * Use [subscribe], but all [block]s will be called inside of [safely] function.
 * Use [onException] to set up your reaction for [Throwable]s
 */
inline fun <T> Flow<T>.subscribeSafely(
    scope: CoroutineScope,
    noinline onException: ExceptionHandler<Unit> = defaultSafelyExceptionHandler,
    noinline block: suspend (T) -> Unit
) = subscribe(scope) {
    safely(onException) {
        block(it)
    }
}

/**
 * Use [subscribeSafelyWithoutExceptions], but all exceptions inside of [safely] will be skipped
 */
inline fun <T> Flow<T>.subscribeSafelyWithoutExceptions(
    scope: CoroutineScope,
    noinline block: suspend (T) -> Unit
) = subscribeSafely(
    scope,
    {},
    block
)
