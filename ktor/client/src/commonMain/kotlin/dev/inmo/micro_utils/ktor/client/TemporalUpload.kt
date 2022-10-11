package dev.inmo.micro_utils.ktor.client

import dev.inmo.micro_utils.common.MPPFile
import dev.inmo.micro_utils.ktor.common.*
import io.ktor.client.HttpClient

expect suspend fun HttpClient.tempUpload(
    fullTempUploadDraftPath: String,
    file: MPPFile,
    onUpload: (uploaded: Long, count: Long) -> Unit = { _, _ -> }
): TemporalFileId
