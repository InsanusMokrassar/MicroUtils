package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@Deprecated(
    "This method will be removed soon. Use launchLogging instead",
    ReplaceWith("this.launchLogging(context = context, start = start, block = block)")
)
fun CoroutineScope.launchSafely(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    onException: ExceptionHandler<Unit> = defaultSafelyExceptionHandler,
    block: suspend CoroutineScope.() -> Unit
) = launch(context, start) {
    runCatchingSafely(onException) {
        block()
    }
}

@Deprecated(
    "This method will be removed soon. Use launchLoggingDropExceptions instead",
    ReplaceWith("this.launchLoggingDropExceptions(context = context, start = start, block = block)")
)
fun CoroutineScope.launchSafelyWithoutExceptions(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    onException: ExceptionHandler<Unit?> = defaultSafelyWithoutExceptionHandlerWithNull,
    block: suspend CoroutineScope.() -> Unit
) = launch(context, start) {
    runCatchingSafelyWithoutExceptions(onException) {
        block()
    }
}

@Deprecated(
    "This method will be removed soon. Use asyncLogging instead",
    ReplaceWith("this.asyncLogging(context = context, start = start, block = block)")
)
fun <T> CoroutineScope.asyncSafely(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    onException: ExceptionHandler<T> = defaultSafelyExceptionHandler,
    block: suspend CoroutineScope.() -> T
) = async(context, start) {
    runCatchingSafely(onException) {
        block()
    }
}

@Deprecated(
    "This method will be removed soon. Use asyncLoggingDropExceptions instead",
    ReplaceWith("this.asyncLoggingDropExceptions(context = context, start = start, block = block)")
)
fun <T> CoroutineScope.asyncSafelyWithoutExceptions(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    onException: ExceptionHandler<T?> = defaultSafelyWithoutExceptionHandlerWithNull,
    block: suspend CoroutineScope.() -> T
) = async(context, start) {
    runCatchingSafelyWithoutExceptions(onException) {
        block()
    }
}
