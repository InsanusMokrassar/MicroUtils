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

suspend inline fun <T> runCatchingSafely(
    onException: ExceptionHandler<T>,
    block: suspend () -> T
): Result<T> {
    return runCatching {
        block()
    }.onFailure {
        coroutineContext[ContextSafelyExceptionHandlerKey] ?.handler ?.invoke(it)
        return runCatching {
            onException(it)
        }
    }
}

suspend inline fun <T> runCatchingSafely(
    block: suspend () -> T
): Result<T> = runCatchingSafely(defaultSafelyExceptionHandler, block)

/**
 * @return [ContextSafelyExceptionHandler] from [coroutineContext] by key [ContextSafelyExceptionHandlerKey] if
 * exists
 *
 * @see ContextSafelyExceptionHandler
 * @see ContextSafelyExceptionHandlerKey
 */
suspend fun contextSafelyExceptionHandler() = coroutineContext[ContextSafelyExceptionHandlerKey]

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
    block: suspend () -> T
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
 * Remember, that [ExceptionHandler] from [CoroutineContext.get] will be used anyway if it is available. After it will
 * be called [onException]
 *
 * @param [onException] Will be called when happen exception inside of [block]. By default will throw exception - this
 * exception will be available for catching
 *
 * @see defaultSafelyExceptionHandler
 * @see safelyWithoutExceptions
 * @see safelyWithContextExceptionHandler
 */
suspend inline fun <T> safely(
    onException: ExceptionHandler<T>,
    block: suspend () -> T
): T = runCatchingSafely(onException, block).getOrThrow()

/**
 * It will run [block] inside of [supervisorScope] to avoid problems with catching of exceptions
 *
 * Priorities of [ExceptionHandler]s:
 *
 * * [onException] In case if custom (will be used anyway if not [defaultSafelyExceptionHandler])
 * * [CoroutineContext.get] with [SafelyExceptionHandlerKey] as key
 * * [defaultSafelyExceptionHandler]
 *
 * Remember, that [ExceptionHandler] from [CoroutineContext.get] will be used anyway if it is available. After it will
 * be called [onException]
 *
 * @param [onException] Will be called when happen exception inside of [block]. By default will throw exception - this
 * exception will be available for catching
 *
 * @see defaultSafelyExceptionHandler
 * @see safelyWithoutExceptions
 * @see safelyWithContextExceptionHandler
 */
suspend inline fun <T> safely(
    block: suspend () -> T
): T = safely(defaultSafelyExceptionHandler, block)

suspend fun <T, R> T.runCatchingSafely(
    onException: ExceptionHandler<R> = defaultSafelyExceptionHandler,
    block: suspend T.() -> R
): Result<R> = runCatching {
    safely(onException) { block() }
}

@Deprecated("Renamed", ReplaceWith("runCatchingSafely(block)", "dev.inmo.micro_utils.coroutines.runCatchingSafely"))
suspend fun <T> safelyWithResult(
    block: suspend () -> T
): Result<T> = runCatchingSafely(defaultSafelyExceptionHandler, block)

suspend fun <T, R> T.safelyWithResult(
    block: suspend T.() -> R
): Result<R> = runCatchingSafely(defaultSafelyExceptionHandler, block)

/**
 * Use this handler in cases you wish to include handling of exceptions by [defaultSafelyWithoutExceptionHandler] and
 * returning null at one time
 *
 * @see safelyWithoutExceptions
 * @see launchSafelyWithoutExceptions
 * @see asyncSafelyWithoutExceptions
 */
val defaultSafelyWithoutExceptionHandlerWithNull: ExceptionHandler<Nothing?> = {
    defaultSafelyWithoutExceptionHandler.invoke(it)
    null
}

/**
 * Shortcut for [safely] with exception handler, that as expected must return null in case of impossible creating of
 * result from exception (instead of throwing it, by default always returns null)
 */
suspend fun <T> safelyWithoutExceptions(
    onException: ExceptionHandler<T> = defaultSafelyExceptionHandler,
    block: suspend () -> T
): T? = runCatchingSafely(onException, block).getOrNull()

suspend fun <T> runCatchingSafelyWithoutExceptions(
    onException: ExceptionHandler<T?> = defaultSafelyExceptionHandler,
    block: suspend () -> T
): Result<T?> = runCatchingSafely(onException, block).let {
    if (it.isFailure) return Result.success<T?>(null)

    it
}

fun CoroutineScopeWithDefaultFallback(
    context: CoroutineContext,
    defaultExceptionsHandler: ExceptionHandler<Unit>
) = CoroutineScope(
    context + ContextSafelyExceptionHandler(defaultExceptionsHandler)
)
