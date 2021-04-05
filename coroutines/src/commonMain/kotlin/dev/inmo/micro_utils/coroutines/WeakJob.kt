package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

private fun CoroutineScope.createWeakSubScope() = CoroutineScope(coroutineContext.minusKey(Job)).also { newScope ->
    coroutineContext.job.invokeOnCompletion { newScope.cancel() }
}

fun CoroutineScope.weakLaunch(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job {
    val scope = createWeakSubScope()
    val job = scope.launch(context, start, block)
    job.invokeOnCompletion { scope.cancel() }
    return job
}

fun <T> CoroutineScope.weakAsync(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> T
): Deferred<T> {
    val scope = createWeakSubScope()
    val deferred = scope.async(context, start, block)
    deferred.invokeOnCompletion { scope.cancel() }
    return deferred
}
