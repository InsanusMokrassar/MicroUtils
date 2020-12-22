package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext
import kotlin.reflect.KClass

typealias ExceptionHandler<T> = suspend (Throwable) -> T

/**
 * This instance will be used in all calls of [safely] where exception handler has not been passed
 */
var defaultSafelyExceptionHandler: ExceptionHandler<Nothing> = { throw it }

/**
 * Key for [SafelyExceptionHandler] which can be used in [CoroutineContext.get] to get current default
 * [SafelyExceptionHandler]
 */
class SafelyExceptionHandlerKey<T>() : CoroutineContext.Key<SafelyExceptionHandler<T>>
private val nothingSafelyEceptionHandlerKey = SafelyExceptionHandlerKey<Nothing>()
private val unitSafelyEceptionHandlerKey = SafelyExceptionHandlerKey<Unit>()

private val exceptionHandlersKeysCache = mutableMapOf<>()
/**
 * Shortcut for creating instance of [SafelyExceptionHandlerKey]
 */
@Suppress("NOTHING_TO_INLINE")
inline fun <T> safelyExceptionHandlerKey() = SafelyExceptionHandlerKey(T::class)

/**
 * Shortcut for getting instance of [SafelyExceptionHandler] from current [coroutineContext]
 */
@Suppress("NOTHING_TO_INLINE")
suspend inline fun <reified T : Any> safelyExceptionHandler() = coroutineContext[safelyExceptionHandlerKey<T>()]
@Suppress("NOTHING_TO_INLINE")
inline fun <reified T : Any> ExceptionHandler<T>.safelyExceptionHandler() = SafelyExceptionHandler(this, T::class)

/**
 * Wrapper for [ExceptionHandler] which can be used in [CoroutineContext] to set local (for [CoroutineContext]) default
 * [ExceptionHandler]. To get it use [CoroutineContext.get] with key [SafelyExceptionHandlerKey]
 *
 * @see SafelyExceptionHandlerKey
 * @see ExceptionHandler
 */
class SafelyExceptionHandler<T : Any>(
    val handler: ExceptionHandler<T>,
    returnKClass: KClass<T>
) : CoroutineContext.Element {
    override val key: CoroutineContext.Key<*> = SafelyExceptionHandlerKey(returnKClass)
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
    onException: ExceptionHandler<T> = defaultSafelyExceptionHandler,
    block: suspend CoroutineScope.() -> T
): T {
    val contextHandler = if (onException === defaultSafelyExceptionHandler) {
        coroutineContext[nothingSafelyEceptionHandlerKey] ?:
        safelyExceptionHandler<Unit>() ?.let { unitHandler ->
            val handler = unitHandler.handler
            SafelyExceptionHandler<T> {
                handler(it)
                onException(it)
            }
        } ?:
        onException.safelyExceptionHandler()
    } else {
        onException.safelyExceptionHandler()
    }
    return try {
        withContext(contextHandler) {
            supervisorScope(block)
        }
    } catch (e: Throwable) {
        contextHandler.handler(e)
    }
}

/**
 * Shortcut for [safely] without exception handler (instead of this you will receive null as a result)
 */
suspend inline fun <T> safelyWithoutExceptions(
    noinline block: suspend CoroutineScope.() -> T
): T? = safely({ null }, block)
