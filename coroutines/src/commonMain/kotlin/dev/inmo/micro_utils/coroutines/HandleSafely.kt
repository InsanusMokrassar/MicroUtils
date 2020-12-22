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

class SafelyExceptionHandlerKey<T> : CoroutineContext.Key<SafelyExceptionHandler<T>>
inline fun <T> safelyExceptionHandlerKey() = SafelyExceptionHandlerKey<T>()
class SafelyExceptionHandler<T>(
    val handler: ExceptionHandler<T>
) : CoroutineContext.Element {

    override val key: CoroutineContext.Key<*> = safelyExceptionHandlerKey<T>()
}

/**
 * It will run [block] inside of [supervisorScope] to avoid problems with catching of exceptions
 *
 * @param [onException] Will be called when happen exception inside of [block]. By default will throw exception - this
 * exception will be available for catching
 *
 * @see defaultSafelyExceptionHandler
 * @see safelyWithoutExceptions
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
