package dev.inmo.micro_utils.ktor.client

import dev.inmo.micro_utils.common.MPPFile
import dev.inmo.micro_utils.common.filename
import dev.inmo.micro_utils.ktor.common.TemporalFileId
import dev.inmo.micro_utils.mime_types.getMimeTypeOrAny
import io.ktor.client.HttpClient
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.statement.bodyAsText
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders

internal val MPPFile.mimeType: String
    get() = getMimeTypeOrAny(filename.extension).raw

actual suspend fun HttpClient.tempUpload(
    fullTempUploadDraftPath: String,
    file: MPPFile,
    onUpload: OnUploadCallback
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
