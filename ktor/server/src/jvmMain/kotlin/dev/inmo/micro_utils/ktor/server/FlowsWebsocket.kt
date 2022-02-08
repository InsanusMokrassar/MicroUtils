package dev.inmo.micro_utils.ktor.server

import dev.inmo.micro_utils.coroutines.safely
import dev.inmo.micro_utils.ktor.common.*
import io.ktor.application.featureOrNull
import io.ktor.application.install
import io.ktor.http.cio.websocket.*
import io.ktor.routing.Route
import io.ktor.routing.application
import io.ktor.websocket.webSocket
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.SerializationStrategy

fun <T> Route.includeWebsocketHandling(
    suburl: String,
    flow: Flow<T>,
    converter: (T) -> StandardKtorSerialInputData
) {
    application.apply {
        featureOrNull(io.ktor.websocket.WebSockets) ?: install(io.ktor.websocket.WebSockets)
    }
    webSocket(suburl) {
        safely {
            flow.collect {
                send(converter(it))
            }
        }
    }
}

fun <T> Route.includeWebsocketHandling(
    suburl: String,
    flow: Flow<T>,
    serializer: SerializationStrategy<T>,
    serialFormat: StandardKtorSerialFormat = standardKtorSerialFormat
) = includeWebsocketHandling(
    suburl,
    flow
) {
    serialFormat.encodeDefault(serializer, it)
}
