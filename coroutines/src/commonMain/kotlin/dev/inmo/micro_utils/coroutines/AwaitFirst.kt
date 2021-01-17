package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.*
import kotlin.coroutines.*

suspend fun <T> Iterable<Deferred<T>>.awaitFirst(
    scope: CoroutineScope,
    cancelOnResult: Boolean = true
): T = suspendCoroutine<T> { continuation ->
    scope.launch(SupervisorJob()) {
        val scope = this
        forEach {
            scope.launch {
                continuation.resume(it.await())
                scope.cancel()
            }
        }
    }
}.also {
    if (cancelOnResult) {
        forEach {
            try {
                it.cancel()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }
        }
    }
}
suspend fun <T> Iterable<Deferred<T>>.awaitFirst(
    cancelOthers: Boolean = true
): T = awaitFirst(CoroutineScope(coroutineContext), cancelOthers)
