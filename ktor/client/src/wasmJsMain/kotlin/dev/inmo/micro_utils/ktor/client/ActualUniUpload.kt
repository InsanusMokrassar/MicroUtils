package dev.inmo.micro_utils.ktor.client

import dev.inmo.micro_utils.common.MPPFile
import dev.inmo.micro_utils.coroutines.LinkedSupervisorJob
import dev.inmo.micro_utils.coroutines.launchLoggingDropExceptions
import io.ktor.client.HttpClient
import io.ktor.client.content.*
import io.ktor.http.Headers
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.job
import kotlinx.io.readByteArray
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.StringFormat
import kotlinx.serialization.encodeToString
import org.khronos.webgl.toInt8Array
import org.w3c.files.Blob
import org.w3c.xhr.FormData
import org.w3c.xhr.TEXT
import org.w3c.xhr.XMLHttpRequest
import org.w3c.xhr.XMLHttpRequestResponseType

/**
 * Will execute submitting of multipart data request
 *
 * @param data [Map] where keys will be used as names for multipart parts and values as values. If you will pass
 * [dev.inmo.micro_utils.common.MPPFile] (File from JS or JVM platform). Also you may pass [UniUploadFileInfo] as value
 * in case you wish to pass other source of multipart binary data than regular file
 * @suppress
 */
actual suspend fun <T> HttpClient.uniUpload(
    url: String,
    data: Map<String, Any>,
    resultDeserializer: DeserializationStrategy<T>,
    headers: Headers,
    stringFormat: StringFormat,
    onUpload: ProgressListener
): T? {
    val formData = FormData()
    val answer = CompletableDeferred<T?>(currentCoroutineContext().job)
    val subscope = CoroutineScope(currentCoroutineContext().LinkedSupervisorJob())

    data.forEach { (k, v) ->
        when (v) {
            is MPPFile -> formData.append(
                k,
                v
            )
            is UniUploadFileInfo -> formData.append(
                k,
                Blob(arrayOf(v.inputAllocator().readByteArray().toInt8Array()).toJsArray().unsafeCast()),
                v.fileName.name
            )
            else -> formData.append(
                k,
                stringFormat.encodeToString(v)
            )
        }
    }

    val request = XMLHttpRequest()
    headers.forEach { s, strings ->
        request.setRequestHeader(s, strings.joinToString())
    }
    request.responseType = XMLHttpRequestResponseType.TEXT
    request.upload.onprogress = {
        subscope.launchLoggingDropExceptions { onUpload.onProgress(it.loaded.toString().toLong(), it.total.toString().toLong()) }
    }
    request.onload = {
        if (request.status == 200.toShort()) {
            answer.complete(
                stringFormat.decodeFromString(resultDeserializer, request.responseText)
            )
        } else {
            answer.completeExceptionally(Exception("Something went wrong: $it"))
        }
    }
    request.onerror = {
        answer.completeExceptionally(Exception("Something went wrong: $it"))
    }
    request.open("POST", url, true)
    request.send(formData)

    answer.invokeOnCompletion {
        runCatching {
            if (request.readyState != XMLHttpRequest.DONE) {
                request.abort()
            }
        }
    }

    return answer.await().also {
        subscope.cancel()
    }
}
