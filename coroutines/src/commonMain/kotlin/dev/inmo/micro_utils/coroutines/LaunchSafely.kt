package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun CoroutineScope.launchSafely(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    onException: ExceptionHandler<Unit> = defaultSafelyExceptionHandler,
    block: suspend () -> Unit
) = launch(context, start) {
    runCatchingSafely(onException, block = block)
}

fun CoroutineScope.launchSafelyWithoutExceptions(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    onException: ExceptionHandler<Unit?> = defaultSafelyWithoutExceptionHandlerWithNull,
    block: suspend () -> Unit
) = launch(context, start) {
    runCatchingSafelyWithoutExceptions(onException, block = block)
}

fun <T> CoroutineScope.asyncSafely(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    onException: ExceptionHandler<T> = defaultSafelyExceptionHandler,
    block: suspend () -> T
) = async(context, start) {
    runCatchingSafely(onException, block = block)
}

fun <T> CoroutineScope.asyncSafelyWithoutExceptions(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    onException: ExceptionHandler<T?> = defaultSafelyWithoutExceptionHandlerWithNull,
    block: suspend () -> T
) = async(context, start) {
    runCatchingSafelyWithoutExceptions(onException, block = block)
}
