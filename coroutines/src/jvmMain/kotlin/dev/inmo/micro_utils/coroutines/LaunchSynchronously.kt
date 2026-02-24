package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.*

/**
 * Launches a coroutine and blocks the current thread until the coroutine completes, returning its result.
 * This is useful for bridging between suspending and non-suspending code in JVM environments.
 * The coroutine is launched with [CoroutineStart.UNDISPATCHED] to start execution immediately.
 *
 * @param T The return type of the suspending block
 * @param block The suspending function to execute synchronously
 * @return The result of the suspending block
 * @throws Throwable if the coroutine throws an exception
 */
fun <T> CoroutineScope.launchSynchronously(block: suspend CoroutineScope.() -> T): T {
    var result: Result<T>? = null
    val objectToSynchronize = Object()
    synchronized(objectToSynchronize) {
        launch(start = CoroutineStart.UNDISPATCHED) {
            result = runCatching {
                block()
            }
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

/**
 * Launches a coroutine in a new [CoroutineScope] with [Dispatchers.Default] and blocks the current thread
 * until the coroutine completes, returning its result.
 *
 * @param T The return type of the suspending block
 * @param block The suspending function to execute synchronously
 * @return The result of the suspending block
 * @throws Throwable if the coroutine throws an exception
 */
fun <T> launchSynchronously(block: suspend CoroutineScope.() -> T): T = CoroutineScope(Dispatchers.Default).launchSynchronously(block)

/**
 * Alias for [launchSynchronously]. Launches a coroutine and blocks the current thread until it completes.
 *
 * @param T The return type of the suspending block
 * @param block The suspending function to execute synchronously
 * @return The result of the suspending block
 */
fun <T> CoroutineScope.doSynchronously(block: suspend CoroutineScope.() -> T): T = launchSynchronously(block)

/**
 * Alias for [launchSynchronously]. Launches a coroutine in a new scope and blocks the current thread until it completes.
 *
 * @param T The return type of the suspending block
 * @param block The suspending function to execute synchronously
 * @return The result of the suspending block
 */
fun <T> doSynchronously(block: suspend CoroutineScope.() -> T): T = launchSynchronously(block)
