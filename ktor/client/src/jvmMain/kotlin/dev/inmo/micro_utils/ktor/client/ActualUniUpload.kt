package dev.inmo.micro_utils.ktor.client

import io.ktor.client.HttpClient
import io.ktor.client.content.*
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.forms.InputProvider
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.headers
import io.ktor.client.statement.bodyAsText
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.Parameters
import io.ktor.http.content.PartData
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.StringFormat
import kotlinx.serialization.serializer
import java.io.File

/**
 * Will execute submitting of multipart data request
 *
 * @param data [Map] where keys will be used as names for multipart parts and values as values. If you will pass
 * [dev.inmo.micro_utils.common.MPPFile] (File from JS or JVM platform). Also you may pass [UniUploadFileInfo] as value
 * in case you wish to pass other source of multipart binary data than regular file
 * @suppress
 */
@OptIn(InternalSerializationApi::class)
actual suspend fun <T> HttpClient.uniUpload(
    url: String,
    data: Map<String, Any>,
    resultDeserializer: DeserializationStrategy<T>,
    headers: Headers,
    stringFormat: StringFormat,
    onUpload: ProgressListener
): T? {
    val withBinary = data.values.any { it is File || it is UniUploadFileInfo }

    val formData = formData {
        for (k in data.keys) {
            val v = data[k] ?: continue
            when (v) {
                is File -> append(
                    k,
                    v.inputProviderSync(),
                    Headers.build {
                        append(HttpHeaders.ContentType, v.mimeType)
                        append(HttpHeaders.ContentDisposition, "filename=\"${v.name}\"")
                    }
                )
                is UniUploadFileInfo -> append(
                    k,
                    InputProvider(block = v.inputAllocator),
                    Headers.build {
                        append(HttpHeaders.ContentType, v.mimeType)
                        append(HttpHeaders.ContentDisposition, "filename=\"${v.fileName.name}\"")
                    }
                )
                else -> append(
                    k,
                    stringFormat.encodeToString(v::class.serializer() as SerializationStrategy<in Any>, v)
                )
            }
        }
    }

    val requestBuilder: HttpRequestBuilder.() -> Unit = {
        headers {
            appendAll(headers)
        }
        onUpload { bytesSentTotal, contentLength ->
            onUpload.onProgress(bytesSentTotal, contentLength)
        }
    }

    val response = if (withBinary) {
        submitFormWithBinaryData(
            url,
            formData,
            block = requestBuilder
        )
    } else {
        submitForm(
            url,
            Parameters.build {
                for (it in formData) {
                    val formItem = (it as PartData.FormItem)
                    append(it.name!!, it.value)
                }
            },
            block = requestBuilder
        )
    }

    return if (response.status == HttpStatusCode.OK) {
        stringFormat.decodeFromString(resultDeserializer, response.bodyAsText())
    } else {
        null
    }
}
