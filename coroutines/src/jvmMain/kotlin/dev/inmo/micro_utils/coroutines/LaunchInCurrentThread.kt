package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

/**
 * Launches a coroutine in the current thread using [Dispatchers.Unconfined] and blocks until it completes,
 * returning its result. The coroutine will start execution in the current thread and will continue
 * in the same thread until the first suspension point.
 *
 * @param T The return type of the suspending block
 * @param block The suspending function to execute in the current thread
 * @return The result of the suspending block
 * @throws Throwable if the coroutine throws an exception
 */
fun <T> launchInCurrentThread(block: suspend CoroutineScope.() -> T): T {
    val scope = CoroutineScope(Dispatchers.Unconfined)
    return scope.launchSynchronously(block)
}
