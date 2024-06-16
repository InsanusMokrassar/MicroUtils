package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

/**
 * Launching [block] in [runCatching]. In case of failure, it will:
 *
 * * Try to get [ContextSafelyExceptionHandler] from current [coroutineContext] and call its
 * [ContextSafelyExceptionHandler.handler] invoke. **Thrown exception from its handler
 * will pass out of [runCatchingSafely]**
 * * Execute [onException] inside of new [runCatching] and return its result. Throws exception
 * will be caught by [runCatching] and wrapped in [Result]
 *
 * @return [Result] with result of [block] if no exceptions or [Result] from [onException] execution
 */
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

/**
 * Launching [runCatchingSafely] with [defaultSafelyExceptionHandler] as `onException` parameter
 */
suspend inline fun <T> runCatchingSafely(
    block: suspend () -> T
): Result<T> = runCatchingSafely(defaultSafelyExceptionHandler, block)

//suspend inline fun <T, R> T.runCatchingSafely(
//    onException: ExceptionHandler<R>,
//    block: suspend T.() -> R
//): Result<R> = runCatchingSafely(onException) {
//    block()
//}

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
 * Calls [runCatchingSafely] and getting the result via [Result.getOrThrow]
 *
 * @see runCatchingSafely
 */
suspend inline fun <T> safely(
    onException: ExceptionHandler<T>,
    block: suspend () -> T
): T = runCatchingSafely(onException, block).getOrThrow()

/**
 * Calls [safely] with passing of [defaultSafelyExceptionHandler] as `onException`
 *
 * @see runCatchingSafely
 */
suspend inline fun <T> safely(
    block: suspend () -> T
): T = safely(defaultSafelyExceptionHandler, block)

@Deprecated("Renamed", ReplaceWith("runCatchingSafely(block)", "dev.inmo.micro_utils.coroutines.runCatchingSafely"))
suspend fun <T> safelyWithResult(
    block: suspend () -> T
): Result<T> = runCatchingSafely(defaultSafelyExceptionHandler, block)

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
