package dev.inmo.micro_utils.coroutines

import kotlin.coroutines.*
import kotlin.js.JsExport

/**
 * Call this method in case you need to do something in common thread (like reading of file in JVM)
 */
@JsExport
suspend fun <T> doOutsideOfCoroutine(block: () -> T): T = suspendCoroutine {
    try {
        it.resume(block())
    } catch (e: Throwable) {
        it.resumeWithException(e)
    }
}

