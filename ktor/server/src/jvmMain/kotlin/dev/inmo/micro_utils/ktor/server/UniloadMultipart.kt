package dev.inmo.micro_utils.ktor.server

import dev.inmo.micro_utils.common.*
import dev.inmo.micro_utils.coroutines.safely
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
) = safely {
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
) = safely {
    val multipartData = receiveMultipart()

    var resultInput: MPPFile? = null

    multipartData.forEachPart {
        when (it) {
            is PartData.FormItem -> onFormItem(it)
            is PartData.FileItem -> {
                if (it.name == "bytes") {
                    val name = FileName(it.originalFileName ?: error("File name is unknown for default part"))
                    resultInput = MPPFile.createTempFile(
                        name.nameWithoutExtension.let {
                            var resultName = it
                            while (resultName.length < 3) {
                                resultName += "_"
                            }
                            resultName
                        },
                        ".${name.extension}"
                    ).apply {
                        outputStream().use { fileStream ->
                            it.streamProvider().use {
                                it.copyTo(fileStream)
                            }
                        }
                    }
                } else {
                    onCustomFileItem(it)
                }
            }
            is PartData.BinaryItem -> onBinaryContent(it)
            is PartData.BinaryChannelItem -> onBinaryChannelItem(it)
        }
    }

    resultInput ?: error("Bytes has not been received")
}
