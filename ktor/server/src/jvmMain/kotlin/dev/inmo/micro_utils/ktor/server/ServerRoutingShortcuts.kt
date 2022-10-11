package dev.inmo.micro_utils.ktor.server

import dev.inmo.micro_utils.common.*
import dev.inmo.micro_utils.coroutines.safely
import dev.inmo.micro_utils.ktor.common.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.request.receiveMultipart
import io.ktor.server.response.respond
import io.ktor.server.response.respondBytes
import io.ktor.server.routing.Route
import io.ktor.server.websocket.WebSocketServerSession
import io.ktor.util.pipeline.PipelineContext
import io.ktor.utils.io.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy

suspend fun ApplicationCall.uniloadMultipart(
    onFormItem: (PartData.FormItem) -> Unit = {},
    onCustomFileItem: (PartData.FileItem) -> Unit = {},
    onBinaryChannelItem: (PartData.BinaryChannelItem) -> Unit = {},
    onBinaryContent: (PartData.BinaryItem) -> Unit = {}
) = safely {
    val multipartData = receiveMultipart()

    var resultInput: Input? = null

    multipartData.forEachPart {
        when (it) {
            is PartData.FormItem -> onFormItem(it)
            is PartData.FileItem -> {
                when (it.name) {
                    "bytes" -> resultInput = it.provider()
                    else -> onCustomFileItem(it)
                }
            }
            is PartData.BinaryItem -> onBinaryContent(it)
            is PartData.BinaryChannelItem -> onBinaryChannelItem(it)
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

suspend fun ApplicationCall.getParameterOrSendError(
    field: String
) = parameters[field].also {
    if (it == null) {
        respond(HttpStatusCode.BadRequest, "Request must contains $field")
    }
}

fun ApplicationCall.getQueryParameter(
    field: String
) = request.queryParameters[field]

suspend fun ApplicationCall.getQueryParameterOrSendError(
    field: String
) = getQueryParameter(field).also {
    if (it == null) {
        respond(HttpStatusCode.BadRequest, "Request query parameters must contains $field")
    }
}
