package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.supervisorScope
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

typealias ExceptionHandler<T> = suspend (Throwable) -> T

/**
 * This instance will be used in all calls of [safely] where exception handler has not been passed
 */
var defaultSafelyExceptionHandler: ExceptionHandler<Nothing> = { throw it }

/**
 * Key for [SafelyExceptionHandler] which can be used in [CoroutineContext.get] to get current default
 * [SafelyExceptionHandler]
 */
class SafelyExceptionHandlerKey<T> : CoroutineContext.Key<SafelyExceptionHandler<T>>

/**
 * Shortcut for creating instance of [SafelyExceptionHandlerKey]
 */
@Suppress("NOTHING_TO_INLINE")
inline fun <T> safelyExceptionHandlerKey() = SafelyExceptionHandlerKey<T>()

/**
 * Wrapper for [ExceptionHandler] which can be used in [CoroutineContext] to set local (for [CoroutineContext]) default
 * [ExceptionHandler]. To get it use [CoroutineContext.get] with key [SafelyExceptionHandlerKey]
 *
 * @see SafelyExceptionHandlerKey
 * @see ExceptionHandler
 */
class SafelyExceptionHandler<T>(
    val handler: ExceptionHandler<T>
) : CoroutineContext.Element {

    override val key: CoroutineContext.Key<*> = safelyExceptionHandlerKey<T>()
}

/**
 * It will run [block] inside of [supervisorScope] to avoid problems with catching of exceptions
 *
 * Priorities of [ExceptionHandler]s:
 *
 * * [onException] In case if custom (will be used anyway if not [defaultSafelyExceptionHandler])
 * * [CoroutineContext.get] with [SafelyExceptionHandlerKey] as key
 * * [defaultSafelyExceptionHandler]
 *
 * @param [onException] Will be called when happen exception inside of [block]. By default will throw exception - this
 * exception will be available for catching
 *
 * @see defaultSafelyExceptionHandler
 * @see safelyWithoutExceptions
 * @see SafelyExceptionHandlerKey
 * @see SafelyExceptionHandler
 */
suspend inline fun <T> safely(
    noinline onException: ExceptionHandler<T> = defaultSafelyExceptionHandler,
    noinline block: suspend CoroutineScope.() -> T
): T {
    return try {
        supervisorScope(block)
    } catch (e: Throwable) {
        val handler = if (onException == defaultSafelyExceptionHandler) {
            coroutineContext[safelyExceptionHandlerKey<T>()] ?.handler ?: onException
        } else {
            onException
        }
        handler(e)
    }
}

/**
 * Shortcut for [safely] without exception handler (instead of this you will receive null as a result)
 */
suspend inline fun <T> safelyWithoutExceptions(
    noinline block: suspend CoroutineScope.() -> T
): T? = safely({ null }, block)
