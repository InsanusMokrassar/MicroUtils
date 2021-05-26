package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

inline fun CoroutineScope.launchSafely(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    noinline onException: ExceptionHandler<Unit> = defaultSafelyExceptionHandler,
    noinline block: suspend CoroutineScope.() -> Unit
) = launch(context, start) {
    safely(onException, block)
}

inline fun CoroutineScope.launchSafelyWithoutExceptions(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    noinline onException: ExceptionHandler<Unit?> = defaultSafelyWithoutExceptionHandlerWithNull,
    noinline block: suspend CoroutineScope.() -> Unit
) = launch(context, start) {
    safelyWithoutExceptions(onException, block)
}

inline fun <T> CoroutineScope.asyncSafely(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    noinline onException: ExceptionHandler<T> = defaultSafelyExceptionHandler,
    noinline block: suspend CoroutineScope.() -> T
) = async(context, start) {
    safely(onException, block)
}

inline fun <T> CoroutineScope.asyncSafelyWithoutExceptions(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    noinline onException: ExceptionHandler<T?> = defaultSafelyWithoutExceptionHandlerWithNull,
    noinline block: suspend CoroutineScope.() -> T
) = async(context, start) {
    safelyWithoutExceptions(onException, block)
}
