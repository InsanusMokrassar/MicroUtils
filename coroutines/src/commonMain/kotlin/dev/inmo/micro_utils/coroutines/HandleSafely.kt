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
 * Shortcut for [safely] with exception handler, that as expected must return null in case of impossible creating of
 * result from exception (instead of throwing it)
 */
suspend inline fun <T> safelyWithoutExceptions(
    noinline onException: ExceptionHandler<T?>,
    noinline block: suspend CoroutineScope.() -> T
): T? = safely(onException, block)

/**
 * Shortcut for [safely] without exception handler (instead of this you will always receive null as a result)
 */
suspend inline fun <T> safelyWithoutExceptions(
    noinline block: suspend CoroutineScope.() -> T
): T? = safelyWithoutExceptions(
    {
        defaultSafelyWithoutExceptionHandler.invoke(it)
        null
    },
    block
)
