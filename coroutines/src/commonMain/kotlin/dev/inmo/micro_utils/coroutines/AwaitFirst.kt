package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.*
import kotlin.coroutines.*

suspend fun <T> Iterable<Deferred<T>>.awaitFirstWithDeferred(
    scope: CoroutineScope,
    cancelOnResult: Boolean = true
): Pair<Deferred<T>, T> {
    val resultDeferred = CompletableDeferred<Pair<Deferred<T>, T>>()
    val scope = scope.LinkedSupervisorScope()
    forEach {
        scope.launch {
            resultDeferred.complete(it to it.await())
            scope.cancel()
        }
    }
    return resultDeferred.await().also {
        if (cancelOnResult) {
            forEach {
                runCatchingSafely { it.cancel() }
            }
        }
    }
}

suspend fun <T> Iterable<Deferred<T>>.awaitFirst(
    scope: CoroutineScope,
    cancelOnResult: Boolean = true
): T = awaitFirstWithDeferred(scope, cancelOnResult).second
suspend fun <T> Iterable<Deferred<T>>.awaitFirst(
    cancelOthers: Boolean = true
): T = awaitFirst(CoroutineScope(coroutineContext), cancelOthers)
