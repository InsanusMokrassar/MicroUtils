package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.*

fun <T> CoroutineScope.launchSynchronously(block: suspend CoroutineScope.() -> T): T {
    val deferred = CompletableDeferred<T>()
    val objectToSynchronize = java.lang.Object()
    val launchCallback = {
        launch {
            safely(
                {
                    deferred.completeExceptionally(it)
                }
            ) {
                deferred.complete(block())
            }
            synchronized(objectToSynchronize) {
                objectToSynchronize.notifyAll()
            }
        }
    }
    synchronized(objectToSynchronize) {
        launchCallback()
        objectToSynchronize.wait()
    }
    return deferred.getCompleted()
}

fun <T> launchSynchronously(block: suspend CoroutineScope.() -> T): T = CoroutineScope(Dispatchers.Default).launchSynchronously(block)

fun <T> CoroutineScope.doSynchronously(block: suspend CoroutineScope.() -> T): T = launchSynchronously(block)
fun <T> doSynchronously(block: suspend CoroutineScope.() -> T): T = launchSynchronously(block)
