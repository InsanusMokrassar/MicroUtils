package dev.inmo.micro_utils.ktor.server

import dev.inmo.micro_utils.coroutines.safely
import dev.inmo.micro_utils.ktor.common.*
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondBytes
import io.ktor.routing.Route
import io.ktor.util.pipeline.PipelineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.*

class UnifiedRouter(
    private val serialFormat: StandardKtorSerialFormat = standardKtorSerialFormat,
    private val serialFormatContentType: ContentType = standardKtorSerialFormatContentType
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
