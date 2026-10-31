package dev.inmo.micro_utils.ktor.client

import dev.inmo.micro_utils.common.MPPFile
import dev.inmo.micro_utils.common.filename
import dev.inmo.micro_utils.ktor.common.TemporalFileId
import io.ktor.client.HttpClient
import io.ktor.client.content.*
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.statement.bodyAsText
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import java.net.URLConnection

internal val MPPFile.mimeType: String
    get() = URLConnection.getFileNameMap().getContentTypeFor(filename.name) ?: "*/*"

actual suspend fun HttpClient.tempUpload(
    fullTempUploadDraftPath: String,
    file: MPPFile,
    onUpload: ProgressListener
): TemporalFileId {
    val inputProvider = file.inputProvider()
    val fileId = submitFormWithBinaryData(
        fullTempUploadDraftPath,
        formData = formData {
            append(
                "data",
                inputProvider,
                Headers.build {
                    append(HttpHeaders.ContentType, file.mimeType)
                    append(HttpHeaders.ContentDisposition, "filename=\"${file.filename.string}\"")
                }
            )
        }
    ) {
        onUpload(onUpload)
    }.bodyAsText()
    return TemporalFileId(fileId)
}
