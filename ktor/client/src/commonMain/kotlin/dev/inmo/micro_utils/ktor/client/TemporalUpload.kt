package dev.inmo.micro_utils.ktor.client

import dev.inmo.micro_utils.common.MPPFile
import dev.inmo.micro_utils.ktor.common.*
import io.ktor.client.HttpClient
import io.ktor.client.content.*

/**
 * Uploads a file to a temporary storage on the server.
 * The server should provide an endpoint that accepts multipart uploads and returns a [TemporalFileId].
 *
 * @param fullTempUploadDraftPath The full URL path to the temporary upload endpoint
 * @param file The file to upload
 * @param onUpload Progress callback invoked during upload
 * @return A [TemporalFileId] that can be used to reference the uploaded file
 */
expect suspend fun HttpClient.tempUpload(
    fullTempUploadDraftPath: String,
    file: MPPFile,
    onUpload: ProgressListener = ProgressListener { _, _ -> }
): TemporalFileId
