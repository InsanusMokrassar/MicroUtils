package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

fun <T> launchInCurrentThread(block: suspend () -> T): T {
    val scope = CoroutineScope(Dispatchers.Unconfined)
    return scope.launchSynchronously(block)
}
