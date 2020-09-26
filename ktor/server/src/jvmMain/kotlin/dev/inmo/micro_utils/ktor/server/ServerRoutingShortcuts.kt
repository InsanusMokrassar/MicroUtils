package dev.inmo.micro_utils.ktor.server

import dev.inmo.micro_utils.coroutines.safely
import dev.inmo.micro_utils.ktor.common.*
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondBytes
import io.ktor.util.toByteArray
import kotlinx.serialization.*

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
