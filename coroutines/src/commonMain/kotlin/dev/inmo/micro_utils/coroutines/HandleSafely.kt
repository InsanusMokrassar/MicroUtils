package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.*
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
@Deprecated("This method will be useless in future major update", ReplaceWith("ContextSafelyExceptionHandlerKey", "dev.inmo.micro_utils.coroutines.ContextSafelyExceptionHandler"))
class SafelyExceptionHandlerKey<T> : CoroutineContext.Key<SafelyExceptionHandler<T>>

/**
 * Shortcut for creating instance of [SafelyExceptionHandlerKey]
 */
@Suppress("NOTHING_TO_INLINE")
@Deprecated("This method will be useless in future major update", ReplaceWith("ContextSafelyExceptionHandlerKey", "dev.inmo.micro_utils.coroutines.ContextSafelyExceptionHandler"))
inline fun <T> safelyExceptionHandlerKey() = SafelyExceptionHandlerKey<T>()

/**
 * Wrapper for [ExceptionHandler] which can be used in [CoroutineContext] to set local (for [CoroutineContext]) default
 * [ExceptionHandler]. To get it use [CoroutineContext.get] with key [SafelyExceptionHandlerKey]
 *
 * @see SafelyExceptionHandlerKey
 * @see ExceptionHandler
 */
@Deprecated("This method will be useless in future major update", ReplaceWith("ContextSafelyExceptionHandler", "dev.inmo.micro_utils.coroutines.ContextSafelyExceptionHandler"))
class SafelyExceptionHandler<T>(
    val handler: ExceptionHandler<T>
) : CoroutineContext.Element {
    override val key: CoroutineContext.Key<*> = safelyExceptionHandlerKey<T>()
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

/**
 * @return [ContextSafelyExceptionHandler] from [coroutineContext] by key [ContextSafelyExceptionHandlerKey] if
 * exists
 *
 * @see ContextSafelyExceptionHandler
 * @see ContextSafelyExceptionHandlerKey
 */
suspend inline fun contextSafelyExceptionHandler() = coroutineContext[ContextSafelyExceptionHandlerKey]

/**
 * This method will set new [coroutineContext] with [ContextSafelyExceptionHandler]. In case if [coroutineContext]
 * already contains [ContextSafelyExceptionHandler], [ContextSafelyExceptionHandler.handler] will be used BEFORE
 * [contextExceptionHandler] in case of exception.
 *
 * After all, will be called [withContext] method with created [ContextSafelyExceptionHandler] and block which will call
 * [safely] method with [safelyExceptionHandler] as onException parameter and [block] as execution block
 */
suspend fun <T> safelyWithContextExceptionHandler(
    contextExceptionHandler: ExceptionHandler<Unit>,
    safelyExceptionHandler: ExceptionHandler<T> = defaultSafelyExceptionHandler,
    block: suspend CoroutineScope.() -> T
): T {
    val contextSafelyExceptionHandler = contextSafelyExceptionHandler() ?.handler ?.let { oldHandler ->
        ContextSafelyExceptionHandler {
            oldHandler(it)
            contextExceptionHandler(it)
        }
    } ?: ContextSafelyExceptionHandler(contextExceptionHandler)
    return withContext(contextSafelyExceptionHandler) {
        safely(safelyExceptionHandler, block)
    }
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
 * @see safelyWithContextExceptionHandler
 */
suspend inline fun <T> safely(
    noinline onException: ExceptionHandler<T> = defaultSafelyExceptionHandler,
    noinline block: suspend CoroutineScope.() -> T
): T {
    return try {
        supervisorScope(block)
    } catch (e: Throwable) {
        coroutineContext[ContextSafelyExceptionHandlerKey] ?.handler ?.invoke(e)
        onException(e)
    }
}

/**
 * Shortcut for [safely] without exception handler (instead of this you will receive null as a result)
 */
suspend inline fun <T> safelyWithoutExceptions(
    noinline block: suspend CoroutineScope.() -> T
): T? = safely({ null }, block)
