package dev.inmo.micro_utils.ktor.server

import dev.inmo.micro_utils.common.*
import dev.inmo.micro_utils.coroutines.safely
import dev.inmo.micro_utils.ktor.common.*
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.request.receive
import io.ktor.request.receiveMultipart
import io.ktor.response.respond
import io.ktor.response.respondBytes
import io.ktor.routing.Route
import io.ktor.util.pipeline.PipelineContext
import io.ktor.utils.io.core.Input
import io.ktor.utils.io.core.readBytes
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.*
import java.io.File.createTempFile

class UnifiedRouter(
    val serialFormat: StandardKtorSerialFormat = standardKtorSerialFormat,
    val serialFormatContentType: ContentType = standardKtorSerialFormatContentType
) {
    fun <T> Route.includeWebsocketHandling(
        suburl: String,
        flow: Flow<T>,
        serializer: SerializationStrategy<T>
    ) = includeWebsocketHandling(suburl, flow, serializer, serialFormat)

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
        }
    }

    resultInput ?: error("Bytes has not been received")
}

suspend fun <T> ApplicationCall.uniloadMultipart(
    deserializer: DeserializationStrategy<T>,
    onFormItem: (PartData.FormItem) -> Unit = {},
    onCustomFileItem: (PartData.FileItem) -> Unit = {},
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
        onBinaryContent
    )

    val completeData = data ?: error("Data has not been received")
    return resultInput to (completeData.dataOrNull().let { it as T })
}

suspend fun <T> ApplicationCall.uniloadMultipartFile(
    deserializer: DeserializationStrategy<T>,
    onFormItem: (PartData.FormItem) -> Unit = {},
    onCustomFileItem: (PartData.FileItem) -> Unit = {},
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
                            name.nameWithoutExtension,
                            ".${name.extension}"
                        )
                    }
                    "data" -> data = standardKtorSerialFormat.decodeDefault(deserializer, it.provider().readBytes()).optional
                    else -> onCustomFileItem(it)
                }
            }
            is PartData.BinaryItem -> onBinaryContent(it)
        }
    }

    val completeData = data ?: error("Data has not been received")
    (resultInput ?: error("Bytes has not been received")) to (completeData.dataOrNull().let { it as T })
}

suspend fun ApplicationCall.uniloadMultipartFile(
    onFormItem: (PartData.FormItem) -> Unit = {},
    onCustomFileItem: (PartData.FileItem) -> Unit = {},
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
                        name.nameWithoutExtension,
                        ".${name.extension}"
                    )
                } else {
                    onCustomFileItem(it)
                }
            }
            is PartData.BinaryItem -> onBinaryContent(it)
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
