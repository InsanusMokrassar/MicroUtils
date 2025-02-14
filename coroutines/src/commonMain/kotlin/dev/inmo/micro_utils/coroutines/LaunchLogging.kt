package dev.inmo.micro_utils.coroutines

import dev.inmo.kslog.common.KSLog
import dev.inmo.kslog.common.e
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

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
