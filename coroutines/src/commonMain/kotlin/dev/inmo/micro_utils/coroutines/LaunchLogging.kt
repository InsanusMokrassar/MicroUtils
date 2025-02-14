package dev.inmo.micro_utils.coroutines

import dev.inmo.kslog.common.KSLog
import dev.inmo.kslog.common.e
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun CoroutineScope.launchLogging(
    errorMessageBuilder: () -> Any = { "Something web wrong" },
    logger: KSLog = KSLog,
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) = launch(context, start) {
    runCatching { block() }.onFailure {
        logger.e(it, errorMessageBuilder)
    }.getOrThrow()
}

fun CoroutineScope.launchLoggingDropExceptions(
    errorMessageBuilder: () -> Any = { "Something web wrong" },
    logger: KSLog = KSLog,
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) = launch(context, start) {
    runCatching { block() }.onFailure {
        logger.e(it, errorMessageBuilder)
    } // just dropping exception
}

fun <T> CoroutineScope.asyncLogging(
    errorMessageBuilder: () -> Any = { "Something web wrong" },
    logger: KSLog = KSLog,
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> T
) = async(context, start) {
    runCatching { block() }.onFailure {
        logger.e(it, errorMessageBuilder)
    }.getOrThrow()
}

fun <T> CoroutineScope.asyncLoggingDropExceptions(
    errorMessageBuilder: () -> Any = { "Something web wrong" },
    logger: KSLog = KSLog,
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> T
) = async(context, start) {
    runCatching { block() }.onFailure {
        logger.e(it, errorMessageBuilder)
    }
}
