package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Created [CoroutineScope] which will [launch] listening of [context] job completing and drop itself. Current weak
 * scope **will not** be attached to [context] directly. So, this [CoroutineScope] will not prevent parent one from
 * cancelling if it is launched with [supervisorScope] or [coroutineScope], but still will follow closing status
 * of parent [Job]
 */
fun WeakScope(
    context: CoroutineContext
) = CoroutineScope(context.minusKey(Job) + Job()).also { newScope ->
    newScope.launch {
        context.job.join()
        newScope.cancel()
    }
}

/**
 * Created [CoroutineScope] which will [launch] listening of [scope] [CoroutineContext] job completing and drop itself. Current weak
 * scope **will not** be attached to [scope] [CoroutineContext] directly. So, this [CoroutineScope] will not prevent parent one from
 * cancelling if it is launched with [supervisorScope] or [coroutineScope], but still will follow closing status
 * of parent [Job]
 */
fun WeakScope(
    scope: CoroutineScope
) = WeakScope(scope.coroutineContext)

/**
 * [this] [CoroutineScope] will be used as base for [WeakScope]. Other parameters ([context], [start], [block])
 * will be used to [launch] [Job]
 */
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

/**
 * [this] [CoroutineScope] will be used as base for [WeakScope]. Other parameters ([context], [start], [block])
 * will be used to create [async] [Deferred]
 */
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
