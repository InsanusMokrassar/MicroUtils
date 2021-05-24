package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.*

inline fun CoroutineScope.launchSafely(
    noinline onException: ExceptionHandler<Unit> = defaultSafelyExceptionHandler,
    noinline block: suspend CoroutineScope.() -> Unit
) = launch {
    safely(onException, block)
}

inline fun CoroutineScope.launchSafelyWithoutExceptions(
    noinline onException: ExceptionHandler<Unit?> = defaultSafelyWithoutExceptionHandlerWithNull,
    noinline block: suspend CoroutineScope.() -> Unit
) = launch {
    safelyWithoutExceptions(onException, block)
}

inline fun <T> CoroutineScope.asyncSafely(
    noinline onException: ExceptionHandler<T> = defaultSafelyExceptionHandler,
    noinline block: suspend CoroutineScope.() -> T
) = async {
    safely(onException, block)
}

inline fun <T> CoroutineScope.asyncSafelyWithoutExceptions(
    noinline onException: ExceptionHandler<T?> = defaultSafelyWithoutExceptionHandlerWithNull,
    noinline block: suspend CoroutineScope.() -> T
) = async {
    safelyWithoutExceptions(onException, block)
}
