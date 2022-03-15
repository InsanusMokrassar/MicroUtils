package dev.inmo.micro_utils.ktor.client

import dev.inmo.micro_utils.common.MPPFile
import dev.inmo.micro_utils.ktor.common.TemporalFileId
import io.ktor.client.HttpClient
import kotlinx.coroutines.*
import org.w3c.xhr.*

suspend fun tempUpload(
    fullTempUploadDraftPath: String,
    file: MPPFile,
    onUpload: (Long, Long) -> Unit
): TemporalFileId {
    val formData = FormData()
    val answer = CompletableDeferred<TemporalFileId>()

    formData.append(
        "data",
        file
    )

    val request = XMLHttpRequest()
    request.responseType = XMLHttpRequestResponseType.TEXT
    request.upload.onprogress = {
        onUpload(it.loaded.toLong(), it.total.toLong())
    }
    request.onload = {
        if (request.status == 200.toShort()) {
            answer.complete(TemporalFileId(request.responseText))
        } else {
            answer.completeExceptionally(Exception("Something went wrong"))
        }
    }
    request.onerror = {
        answer.completeExceptionally(Exception("Something went wrong"))
    }
    request.open("POST", fullTempUploadDraftPath, true)
    request.send(formData)

    currentCoroutineContext().job.invokeOnCompletion {
        runCatching {
            request.abort()
        }
    }

    return answer.await()
}


actual suspend fun HttpClient.tempUpload(
    fullTempUploadDraftPath: String,
    file: MPPFile,
    onUpload: (uploaded: Long, count: Long) -> Unit
): TemporalFileId = dev.inmo.micro_utils.ktor.client.tempUpload(fullTempUploadDraftPath, file, onUpload)
