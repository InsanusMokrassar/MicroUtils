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

class UnifiedRouter(
    val serialFormat: StandardKtorSerialFormat = standardKtorSerialFormat,
    val serialFormatContentType: ContentType = standardKtorSerialFormatContentType
) {
    fun <T> Route.includeWebsocketHandling(
        suburl: String,
        flow: Flow<T>,
        serializer: SerializationStrategy<T>,
        protocol: URLProtocol? = null,
        filter: (suspend WebSocketServerSession.(T) -> Boolean)? = null
    ) = includeWebsocketHandling(suburl, flow, serializer, serialFormat, protocol, filter)

    suspend fun <T> PipelineContext<*, ApplicationCall>.unianswer(
        answerSerializer: SerializationStrategy<T>,
        answer: T
    ) {
        call.respondBytes (
            serialFormat.encodeDefault(answerSerializer, answer),
            serialFormatContentType
        )
    }

    suspend fun <T> PipelineContext<*, ApplicationCall>.uniload(
        deserializer: DeserializationStrategy<T>
    ) = safely {
        serialFormat.decodeDefault(
            deserializer,
            call.receive()
        )
    }

    suspend fun PipelineContext<*, ApplicationCall>.getParameterOrSendError(
        field: String
    ) = call.parameters[field].also {
        if (it == null) {
            call.respond(HttpStatusCode.BadRequest, "Request must contains $field")
        }
    }

    fun PipelineContext<*, ApplicationCall>.getQueryParameter(
        field: String
    ) = call.request.queryParameters[field]

    suspend fun PipelineContext<*, ApplicationCall>.getQueryParameterOrSendError(
        field: String
    ) = getQueryParameter(field).also {
        if (it == null) {
            call.respond(HttpStatusCode.BadRequest, "Request query parameters must contains $field")
        }
    }

    fun <T> PipelineContext<*, ApplicationCall>.decodeUrlQueryValue(
        field: String,
        deserializer: DeserializationStrategy<T>
    ) = getQueryParameter(field) ?.let {
        serialFormat.decodeHex(
            deserializer,
            it
        )
    }

    suspend fun <T> PipelineContext<*, ApplicationCall>.decodeUrlQueryValueOrSendError(
        field: String,
        deserializer: DeserializationStrategy<T>
    ) = decodeUrlQueryValue(field, deserializer).also {
        if (it == null) {
            call.respond(HttpStatusCode.BadRequest, "Request query parameters must contains $field")
        }
    }

    companion object {
        val default
            get() = defaultUnifiedRouter
    }
}

val defaultUnifiedRouter = UnifiedRouter()

suspend fun <T> ApplicationCall.unianswer(
    answerSerializer: SerializationStrategy<T>,
    answer: T
) {
    respondBytes (
        standardKtorSerialFormat.encodeDefault(answerSerializer, answer),
        standardKtorSerialFormatContentType
    )
}

suspend fun <T> ApplicationCall.uniload(
    deserializer: DeserializationStrategy<T>
) = safely {
    standardKtorSerialFormat.decodeDefault(
        deserializer,
        receive()
    )
}

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

suspend fun <T> ApplicationCall.uniloadMultipart(
    deserializer: DeserializationStrategy<T>,
    onFormItem: (PartData.FormItem) -> Unit = {},
    onCustomFileItem: (PartData.FileItem) -> Unit = {},
    onBinaryChannelItem: (PartData.BinaryChannelItem) -> Unit = {},
    onBinaryContent: (PartData.BinaryItem) -> Unit = {}
): Pair<Input, T> {
    var data: Optional<T>? = null
    val resultInput = uniloadMultipart(
        onFormItem,
        {
            if (it.name == "data") {
                data = standardKtorSerialFormat.decodeDefault(deserializer, it.provider().readBytes()).optional
            } else {
                onCustomFileItem(it)
            }
        },
        onBinaryChannelItem,
        onBinaryContent
    )

    val completeData = data ?: error("Data has not been received")
    return resultInput to (completeData.dataOrNull().let { it as T })
}

suspend fun <T> ApplicationCall.uniloadMultipartFile(
    deserializer: DeserializationStrategy<T>,
    onFormItem: (PartData.FormItem) -> Unit = {},
    onCustomFileItem: (PartData.FileItem) -> Unit = {},
    onBinaryChannelItem: (PartData.BinaryChannelItem) -> Unit = {},
    onBinaryContent: (PartData.BinaryItem) -> Unit = {},
) = safely {
    val multipartData = receiveMultipart()

    var resultInput: MPPFile? = null
    var data: Optional<T>? = null

    multipartData.forEachPart {
        when (it) {
            is PartData.FormItem -> onFormItem(it)
            is PartData.FileItem -> {
                when (it.name) {
                    "bytes" -> {
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
                    }
                    "data" -> data = standardKtorSerialFormat.decodeDefault(deserializer, it.provider().readBytes()).optional
                    else -> onCustomFileItem(it)
                }
            }
            is PartData.BinaryItem -> onBinaryContent(it)
            is PartData.BinaryChannelItem -> onBinaryChannelItem(it)
        }
    }

    val completeData = data ?: error("Data has not been received")
    (resultInput ?: error("Bytes has not been received")) to (completeData.dataOrNull().let { it as T })
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

fun <T> ApplicationCall.decodeUrlQueryValue(
    field: String,
    deserializer: DeserializationStrategy<T>
) = getQueryParameter(field) ?.let {
    standardKtorSerialFormat.decodeHex(
        deserializer,
        it
    )
}

suspend fun <T> ApplicationCall.decodeUrlQueryValueOrSendError(
    field: String,
    deserializer: DeserializationStrategy<T>
) = decodeUrlQueryValue(field, deserializer).also {
    if (it == null) {
        respond(HttpStatusCode.BadRequest, "Request query parameters must contains $field")
    }
}
