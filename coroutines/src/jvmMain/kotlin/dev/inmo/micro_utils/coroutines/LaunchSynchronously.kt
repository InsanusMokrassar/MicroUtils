package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.*

fun <T> CoroutineScope.launchSynchronously(block: suspend () -> T): T {
    var result: Result<T>? = null
    val objectToSynchronize = Object()
    synchronized(objectToSynchronize) {
        launch(start = CoroutineStart.UNDISPATCHED) {
            result = runCatchingSafely(block)
        }.invokeOnCompletion {
            synchronized(objectToSynchronize) {
                objectToSynchronize.notifyAll()
            }
        }
        while (result == null) {
            objectToSynchronize.wait()
        }
    }
    return result!!.getOrThrow()
}

fun <T> launchSynchronously(block: suspend () -> T): T = CoroutineScope(Dispatchers.Default).launchSynchronously(block)

fun <T> CoroutineScope.doSynchronously(block: suspend () -> T): T = launchSynchronously(block)
fun <T> doSynchronously(block: suspend () -> T): T = launchSynchronously(block)
