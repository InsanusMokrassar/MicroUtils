package dev.inmo.micro_utils.coroutines

import dev.inmo.micro_utils.common.MPPFile
import dev.inmo.micro_utils.common.selectFile
import kotlinx.coroutines.CompletableDeferred
import org.w3c.dom.HTMLInputElement

suspend fun selectFile(
    inputSetup: (HTMLInputElement) -> Unit = {}
): MPPFile {
    val result = CompletableDeferred<MPPFile>()

    selectFile(
        inputSetup,
        {
            result.completeExceptionally(it)
        }
    ) {
        result.complete(it)
    }

    return result.await()
}

suspend fun selectFileOrNull(
    inputSetup: (HTMLInputElement) -> Unit = {},
    onFailure: (Throwable) -> Unit = {}
): MPPFile? {
    val result = CompletableDeferred<MPPFile?>()

    selectFile(
        inputSetup,
        {
            result.complete(null)
            onFailure(it)
        }
    ) {
        result.complete(it)
    }

    return result.await()
}
