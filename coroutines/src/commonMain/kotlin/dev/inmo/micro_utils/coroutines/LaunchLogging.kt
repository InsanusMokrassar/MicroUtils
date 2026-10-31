package dev.inmo.micro_utils.coroutines

import dev.inmo.kslog.common.KSLog
import dev.inmo.kslog.common.e
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Launches a new coroutine with automatic exception logging. If an exception occurs, it will be logged
 * using the provided [logger] and then rethrown.
 *
 * @param errorMessageBuilder A function to build the error message from the caught exception. Defaults to "Something web wrong"
 * @param logger The logger instance to use for logging exceptions. Defaults to [KSLog]
 * @param context Additional [CoroutineContext] for the new coroutine. Defaults to [EmptyCoroutineContext]
 * @param start The coroutine start option. Defaults to [CoroutineStart.DEFAULT]
 * @param block The suspending function to execute in the new coroutine
 * @return A [Job] representing the launched coroutine
 */
fun CoroutineScope.launchLogging(
    errorMessageBuilder: CoroutineScope.(Throwable) -> Any = { "Something web wrong" },
    logger: KSLog = KSLog,
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) = launch(context, start) {
    runCatching { block() }.onFailure {
        logger.e(it) { errorMessageBuilder(it) }
    }.getOrThrow()
}

/**
 * Launches a new coroutine with automatic exception logging and dropping. If an exception occurs, it will be logged
 * using the provided [logger] and then dropped (not rethrown), allowing the coroutine to complete normally.
 *
 * @param errorMessageBuilder A function to build the error message from the caught exception. Defaults to "Something web wrong"
 * @param logger The logger instance to use for logging exceptions. Defaults to [KSLog]
 * @param context Additional [CoroutineContext] for the new coroutine. Defaults to [EmptyCoroutineContext]
 * @param start The coroutine start option. Defaults to [CoroutineStart.DEFAULT]
 * @param block The suspending function to execute in the new coroutine
 * @return A [Job] representing the launched coroutine
 */
fun CoroutineScope.launchLoggingDropExceptions(
    errorMessageBuilder: CoroutineScope.(Throwable) -> Any = { "Something web wrong" },
    logger: KSLog = KSLog,
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) = launch(context, start) {
    runCatching { block() }.onFailure {
        logger.e(it) { errorMessageBuilder(it) }
    } // just dropping exception
}

/**
 * Creates a new async coroutine with automatic exception logging. If an exception occurs, it will be logged
 * using the provided [logger] and then rethrown when the [Deferred] is awaited.
 *
 * @param T The return type of the async computation
 * @param errorMessageBuilder A function to build the error message from the caught exception. Defaults to "Something web wrong"
 * @param logger The logger instance to use for logging exceptions. Defaults to [KSLog]
 * @param context Additional [CoroutineContext] for the new coroutine. Defaults to [EmptyCoroutineContext]
 * @param start The coroutine start option. Defaults to [CoroutineStart.DEFAULT]
 * @param block The suspending function to execute that returns a value of type [T]
 * @return A [Deferred] representing the async computation
 */
fun <T> CoroutineScope.asyncLogging(
    errorMessageBuilder: CoroutineScope.(Throwable) -> Any = { "Something web wrong" },
    logger: KSLog = KSLog,
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> T
) = async(context, start) {
    runCatching { block() }.onFailure {
        logger.e(it) { errorMessageBuilder(it) }
    }.getOrThrow()
}

/**
 * Creates a new async coroutine with automatic exception logging and dropping. If an exception occurs, it will be logged
 * using the provided [logger] and wrapped in a [Result], which can be checked when the [Deferred] is awaited.
 *
 * @param T The return type of the async computation
 * @param errorMessageBuilder A function to build the error message from the caught exception. Defaults to "Something web wrong"
 * @param logger The logger instance to use for logging exceptions. Defaults to [KSLog]
 * @param context Additional [CoroutineContext] for the new coroutine. Defaults to [EmptyCoroutineContext]
 * @param start The coroutine start option. Defaults to [CoroutineStart.DEFAULT]
 * @param block The suspending function to execute that returns a value of type [T]
 * @return A [Deferred] containing a [Result] representing the async computation
 */
fun <T> CoroutineScope.asyncLoggingDropExceptions(
    errorMessageBuilder: CoroutineScope.(Throwable) -> Any = { "Something web wrong" },
    logger: KSLog = KSLog,
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> T
) = async(context, start) {
    runCatching { block() }.onFailure {
        logger.e(it) { errorMessageBuilder(it) }
    }
}
