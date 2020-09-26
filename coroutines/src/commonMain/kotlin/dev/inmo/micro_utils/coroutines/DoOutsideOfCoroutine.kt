package dev.inmo.micro_utils.coroutines

import kotlin.coroutines.*

/**
 * Call this method in case you need to do something in common thread (like reading of file in JVM)
 */
suspend fun <T> doOutsideOfCoroutine(block: () -> T): T = suspendCoroutine {
    try {
        it.resume(block())
    } catch (e: Throwable) {
        it.resumeWithException(e)
    }
}

