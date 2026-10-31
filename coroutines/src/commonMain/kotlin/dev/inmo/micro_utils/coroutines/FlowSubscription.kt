@file:Suppress("NOTHING_TO_INLINE")

package dev.inmo.micro_utils.coroutines

import dev.inmo.kslog.common.KSLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Shortcut for chain if [Flow.onEach] and [Flow.launchIn]
 */
inline fun <T> Flow<T>.subscribe(scope: CoroutineScope, noinline block: suspend (T) -> Unit) = onEach(block).launchIn(scope)

/**
 * Use [subscribe], but all [block]s will be called inside of [safely] function.
 * Use [onException] to set up your reaction for [Throwable]s
 */
inline fun <T> Flow<T>.subscribeLogging(
    scope: CoroutineScope,
    noinline errorMessageBuilder: T.(Throwable) -> Any = { "Something web wrong" },
    logger: KSLog = KSLog,
    noinline block: suspend (T) -> Unit
) = subscribe(scope) {
    it.runCatchingLogging(
        errorMessageBuilder,
        logger
    ) {
        block(it)
    }.getOrThrow()
}

/**
 * Use [subscribeSafelyWithoutExceptions], but all exceptions will be passed to [defaultSafelyExceptionHandler]
 */
inline fun <T> Flow<T>.subscribeLoggingDropExceptions(
    scope: CoroutineScope,
    noinline errorMessageBuilder: T.(Throwable) -> Any = { "Something web wrong" },
    logger: KSLog = KSLog,
    noinline block: suspend (T) -> Unit
) = subscribe(scope) {
    it.runCatchingLogging(
        errorMessageBuilder,
        logger
    ) {
        block(it)
    }
}

/**
 * Use [subscribe], but all [block]s will be called inside of [safely] function.
 * Use [onException] to set up your reaction for [Throwable]s
 */
@Deprecated(
    "Will be removed soon due to replacement by subscribeLogging",
    ReplaceWith("this.subscribeLogging(scope = scope, block = block)")
)
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
 * Use [subscribeSafelyWithoutExceptions], but all exceptions will be passed to [defaultSafelyExceptionHandler]
 */
@Deprecated(
    "Will be removed soon due to replacement by subscribeLoggingDropExceptions",
    ReplaceWith("this.subscribeLoggingDropExceptions(scope = scope, block = block)")
)
inline fun <T> Flow<T>.subscribeSafelyWithoutExceptions(
    scope: CoroutineScope,
    noinline onException: ExceptionHandler<T?> = defaultSafelyWithoutExceptionHandlerWithNull,
    noinline block: suspend (T) -> Unit
) = subscribe(scope) {
    safelyWithoutExceptions(onException) {
        block(it)
    }
}

/**
 * Use [subscribeSafelyWithoutExceptions], but all exceptions inside of [safely] will be skipped
 */
@Deprecated(
    "Will be removed soon due to replacement by subscribeLoggingDropExceptions",
    ReplaceWith("this.subscribeLoggingDropExceptions(scope = scope, block = block)")
)
inline fun <T> Flow<T>.subscribeSafelySkippingExceptions(
    scope: CoroutineScope,
    noinline block: suspend (T) -> Unit
) = subscribe(scope) {
    safelyWithoutExceptions({ /* skip exceptions */ }) {
        block(it)
    }
}
