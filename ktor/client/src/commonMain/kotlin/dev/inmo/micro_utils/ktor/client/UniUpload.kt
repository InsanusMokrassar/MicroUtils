package dev.inmo.micro_utils.ktor.client

import dev.inmo.micro_utils.common.FileName
import dev.inmo.micro_utils.ktor.common.LambdaInputProvider
import io.ktor.client.HttpClient
import io.ktor.http.Headers
import io.ktor.utils.io.core.Input
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
 */
expect suspend fun <T> HttpClient.uniupload(
    url: String,
    data: Map<String, Any>,
    resultDeserializer: DeserializationStrategy<T>,
    headers: Headers = Headers.Empty,
    stringFormat: StringFormat = Json.Default
): T?
