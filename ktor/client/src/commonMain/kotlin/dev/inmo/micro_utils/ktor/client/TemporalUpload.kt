package dev.inmo.micro_utils.ktor.client

import dev.inmo.micro_utils.common.MPPFile
import dev.inmo.micro_utils.ktor.common.*
import io.ktor.client.HttpClient
import io.ktor.client.content.*

expect suspend fun HttpClient.tempUpload(
    fullTempUploadDraftPath: String,
    file: MPPFile,
    onUpload: ProgressListener = ProgressListener { _, _ -> }
): TemporalFileId
