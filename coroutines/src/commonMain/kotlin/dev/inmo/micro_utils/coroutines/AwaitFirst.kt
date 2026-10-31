package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.*
import kotlin.coroutines.*

/**
 * Trying to [Deferred.await] on all [this] [Deferred]s. The first [Deferred] completed its work will interrupt all
 * others awaits and, if [cancelOnResult] passed as true (**by default**), will also cancel all the others [Deferred]s
 *
 * @param scope Will be used to create [CoroutineScope.LinkedSupervisorScope] and launch joining of all [Job]s in it
 */
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

/**
 * Trying to [Deferred.await] on all [this] [Deferred]s. The first [Deferred] completed its work will interrupt all
 * others awaits and, if [cancelOnResult] passed as true (**by default**), will also cancel all the others [Deferred]s
 *
 * @param scope Will be used to create [CoroutineScope.LinkedSupervisorScope] and launch joining of all [Job]s in it
 */
suspend fun <T> Iterable<Deferred<T>>.awaitFirst(
    scope: CoroutineScope,
    cancelOnResult: Boolean = true
): T = awaitFirstWithDeferred(scope, cancelOnResult).second

/**
 * Trying to [Deferred.await] on all [this] [Deferred]s. The first [Deferred] completed its work will interrupt all
 * others awaits and, if [cancelOthers] passed as true (**by default**), will also cancel all the others [Deferred]s
 *
 * Creates [CoroutineScope] using [coroutineContext] for internal purposes
 */
suspend fun <T> Iterable<Deferred<T>>.awaitFirst(
    cancelOthers: Boolean = true
): T = awaitFirst(CoroutineScope(coroutineContext), cancelOthers)

/**
 * Trying to [Deferred.await] on all [deferreds]. The first [Deferred] completed its work will interrupt all
 * others awaits and, if [cancelOnResult] passed as true (**by default**), will also cancel all the others [deferreds]
 *
 * @param scope Will be used to create [CoroutineScope.LinkedSupervisorScope] and launch joining of all [Job]s in it
 */
suspend fun <T> awaitFirst(
    vararg deferreds: Deferred<T>,
    scope: CoroutineScope,
    cancelOnResult: Boolean = true
): T = deferreds.toList().awaitFirstWithDeferred(scope, cancelOnResult).second
/**
 * Trying to [Deferred.await] on all [deferreds]. The first [Deferred] completed its work will interrupt all
 * others awaits and, if [cancelOthers] passed as true (**by default**), will also cancel all the others [deferreds]
 *
 * Creates [CoroutineScope] using [coroutineContext] for internal purposes
 */
suspend fun <T> awaitFirst(
    vararg deferreds: Deferred<T>,
    cancelOthers: Boolean = true
): T = awaitFirst(*deferreds, scope = CoroutineScope(coroutineContext), cancelOnResult = cancelOthers)
