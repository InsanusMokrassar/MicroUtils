package dev.inmo.micro_utils.ktor.server

import dev.inmo.micro_utils.common.*
import dev.inmo.micro_utils.coroutines.safely
import dev.inmo.micro_utils.ktor.common.downloadToTempFile
import io.ktor.http.content.*
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receiveMultipart
import io.ktor.utils.io.core.*
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.isActive

/**
 * Server-side part which receives [dev.inmo.micro_utils.ktor.client.uniUpload] request
 */
suspend inline fun ApplicationCall.handleUniUpload(
    onFormItem: (PartData.FormItem) -> Unit = {},
    onBinaryContent: (PartData.BinaryItem) -> Unit = {},
    onBinaryChannelItem: (PartData.BinaryChannelItem) -> Unit = {},
    onFileItem: (PartData.FileItem) -> Unit = {}
) {
    val multipartData = receiveMultipart()

    while (currentCoroutineContext().isActive) {
        val partData = multipartData.readPart() ?: break
        when (partData) {
            is PartData.FormItem -> onFormItem(partData)
            is PartData.FileItem -> onFileItem(partData)
            is PartData.BinaryItem -> onBinaryContent(partData)
            is PartData.BinaryChannelItem -> onBinaryChannelItem(partData)
        }
        partData.dispose()
    }
}

suspend fun ApplicationCall.uniloadMultipart(
    onFormItem: (PartData.FormItem) -> Unit = {},
    onCustomFileItem: (PartData.FileItem) -> Unit = {},
    onBinaryChannelItem: (PartData.BinaryChannelItem) -> Unit = {},
    onBinaryContent: (PartData.BinaryItem) -> Unit = {}
): Input = safely {
    var resultInput: Input? = null

    handleUniUpload(
        onFormItem,
        onBinaryContent,
        onBinaryChannelItem
    ) {
        when (it.name) {
            "bytes" -> resultInput = it.provider()
            else -> onCustomFileItem(it)
        }
    }

    resultInput ?: error("Bytes has not been received")
}

suspend fun ApplicationCall.uniloadMultipartFile(
    onFormItem: (PartData.FormItem) -> Unit = {},
    onCustomFileItem: (PartData.FileItem) -> Unit = {},
    onBinaryChannelItem: (PartData.BinaryChannelItem) -> Unit = {},
    onBinaryContent: (PartData.BinaryItem) -> Unit = {},
): MPPFile = safely {
    uniloadMultipart(
        onFormItem,
        onCustomFileItem,
        onBinaryChannelItem,
        onBinaryContent
    ).downloadToTempFile()
}
