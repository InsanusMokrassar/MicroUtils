package dev.inmo.micro_utils.coroutines

import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

typealias ExceptionHandler<T> = suspend (Throwable) -> T

/**
 * This instance will be used in all calls of [safely] where exception handler has not been passed
 */
var defaultSafelyExceptionHandler: ExceptionHandler<Nothing> = { throw it }

/**
 * This instance will be used in all calls of [safelyWithoutExceptions] as an exception handler for [safely] call
 */
var defaultSafelyWithoutExceptionHandler: ExceptionHandler<Unit> = {
    try {
        defaultSafelyExceptionHandler(it)
    } catch (e: Throwable) {
        // do nothing
    }
}

/**
 * This key can (and will) be used to get [ContextSafelyExceptionHandler] from [coroutineContext] of suspend functions
 * and in [ContextSafelyExceptionHandler] for defining of its [CoroutineContext.Element.key]
 *
 * @see safelyWithContextExceptionHandler
 * @see ContextSafelyExceptionHandler
 */
object ContextSafelyExceptionHandlerKey : CoroutineContext.Key<ContextSafelyExceptionHandler>

/**
 * [ExceptionHandler] wrapper which was created to make possible to use [handler] across all coroutines calls
 *
 * @see safelyWithContextExceptionHandler
 * @see ContextSafelyExceptionHandlerKey
 */
class ContextSafelyExceptionHandler(
    val handler: ExceptionHandler<Unit>
) : CoroutineContext.Element {
    override val key: CoroutineContext.Key<*>
        get() = ContextSafelyExceptionHandlerKey
}