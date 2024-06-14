package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun WeakScope(
    context: CoroutineContext
) = CoroutineScope(context.minusKey(Job) + Job()).also { newScope ->
    newScope.launch {
        context.job.join()
        newScope.cancel()
    }
}

fun WeakScope(
    scope: CoroutineScope
) = WeakScope(scope.coroutineContext)

fun CoroutineScope.launchWeak(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job {
    val scope = WeakScope(this)
    val job = scope.launch(context, start, block)
    job.invokeOnCompletion { scope.cancel() }
    return job
}

fun <T> CoroutineScope.asyncWeak(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> T
): Deferred<T> {
    val scope = WeakScope(this)
    val deferred = scope.async(context, start, block)
    deferred.invokeOnCompletion { scope.cancel() }
    return deferred
}
@Deprecated("Renamed", ReplaceWith("launchWeak(context, start, block)", "dev.inmo.micro_utils.coroutines.launchWeak"))
fun CoroutineScope.weakLaunch(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job = launchWeak(context, start, block)

@Deprecated("Renamed", ReplaceWith("asyncWeak(context, start, block)", "dev.inmo.micro_utils.coroutines.asyncWeak"))
fun <T> CoroutineScope.weakAsync(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> T
): Deferred<T> = asyncWeak(context, start, block)
