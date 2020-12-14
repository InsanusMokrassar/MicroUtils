package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.*

fun <T> CoroutineScope.launchSynchronously(block: suspend CoroutineScope.() -> T): T {
    var throwable: Throwable? = null
    var result: T? = null
    val objectToSynchronize = java.lang.Object()
    val launchCallback = {
        launch {
            safely(
                {
                    throwable = it
                }
            ) {
                result = block()
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
    throw throwable ?: return result!!
}

fun <T> launchSynchronously(block: suspend CoroutineScope.() -> T): T = CoroutineScope(Dispatchers.Default).launchSynchronously(block)
