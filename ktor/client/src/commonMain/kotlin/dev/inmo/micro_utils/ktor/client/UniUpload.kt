package dev.inmo.micro_utils.ktor.client

import dev.inmo.micro_utils.common.FileName
import dev.inmo.micro_utils.common.MPPFile
import dev.inmo.micro_utils.ktor.common.LambdaInputProvider
import io.ktor.client.HttpClient
import io.ktor.client.content.*
import io.ktor.http.Headers
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.StringFormat
import kotlinx.serialization.json.Json

data class UniUploadFileInfo(
    val fileName: FileName,
    val mimeType: String,
    val inputAllocator: LambdaInputProvider
)

/**
 * Will execute submitting of multipart data request
 *
 * @param data [Map] where keys will be used as names for multipart parts and values as values. If you will pass
 * [dev.inmo.micro_utils.common.MPPFile] (File from JS or JVM platform). Also you may pass [UniUploadFileInfo] as value
 * in case you wish to pass other source of multipart binary data than regular file
 *
 * @see dev.inmo.micro_utils.ktor.server.handleUniUpload
 */
expect suspend fun <T> HttpClient.uniUpload(
    url: String,
    data: Map<String, Any>,
    resultDeserializer: DeserializationStrategy<T>,
    headers: Headers = Headers.Empty,
    stringFormat: StringFormat = Json,
    onUpload: ProgressListener = ProgressListener { _, _ -> }
): T?

/**
 * Additional variant of [uniUpload] which will unify sending of some [MPPFile] with the server
 *
 * @see dev.inmo.micro_utils.ktor.server.uniloadMultipartFile
 */
suspend fun <T> HttpClient.uniUpload(
    url: String,
    file: MPPFile,
    resultDeserializer: DeserializationStrategy<T>,
    additionalData: Map<String, Any> = emptyMap(),
    headers: Headers = Headers.Empty,
    stringFormat: StringFormat = Json,
    onUpload: ProgressListener = ProgressListener { _, _ -> }
): T? = uniUpload(
    url,
    additionalData + ("bytes" to file),
    resultDeserializer,
    headers,
    stringFormat,
    onUpload
)

/**
 * Additional variant of [uniUpload] which will unify sending of some [UniUploadFileInfo] with the server
 *
 * @see dev.inmo.micro_utils.ktor.server.uniloadMultipartFile
 */
suspend fun <T> HttpClient.uniUpload(
    url: String,
    info: UniUploadFileInfo,
    resultDeserializer: DeserializationStrategy<T>,
    additionalData: Map<String, Any> = emptyMap(),
    headers: Headers = Headers.Empty,
    stringFormat: StringFormat = Json,
    onUpload: ProgressListener = ProgressListener { _, _ -> }
): T? = uniUpload(
    url,
    additionalData + ("bytes" to info),
    resultDeserializer,
    headers,
    stringFormat,
    onUpload
)

/**
 * Additional variant of [uniUpload] which will unify sending of some [UniUploadFileInfo] (built from [fileName],
 * [mimeType] and [inputAllocator]) with the server
 *
 * @see dev.inmo.micro_utils.ktor.server.uniloadMultipartFile
 */
suspend fun <T> HttpClient.uniUpload(
    url: String,
    fileName: FileName,
    mimeType: String,
    inputAllocator: LambdaInputProvider,
    resultDeserializer: DeserializationStrategy<T>,
    additionalData: Map<String, Any> = emptyMap(),
    headers: Headers = Headers.Empty,
    stringFormat: StringFormat = Json,
    onUpload: ProgressListener = ProgressListener { _, _ -> }
): T? = uniUpload(
    url,
    UniUploadFileInfo(fileName, mimeType, inputAllocator),
    resultDeserializer,
    additionalData,
    headers,
    stringFormat,
    onUpload
)
