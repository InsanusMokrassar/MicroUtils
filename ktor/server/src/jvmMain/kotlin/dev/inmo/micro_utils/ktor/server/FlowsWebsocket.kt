package dev.inmo.micro_utils.ktor.server

import dev.inmo.micro_utils.coroutines.safely
import dev.inmo.micro_utils.ktor.common.*
import io.ktor.application.featureOrNull
import io.ktor.application.install
import io.ktor.http.URLProtocol
import io.ktor.http.cio.websocket.*
import io.ktor.routing.Route
import io.ktor.routing.application
import io.ktor.websocket.*
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.SerializationStrategy

fun <T> Route.includeWebsocketHandling(
    suburl: String,
    flow: Flow<T>,
    protocol: URLProtocol = URLProtocol.WS,
    converter: suspend WebSocketServerSession.(T) -> StandardKtorSerialInputData?
) {
    application.apply {
        featureOrNull(io.ktor.websocket.WebSockets) ?: install(io.ktor.websocket.WebSockets)
    }
    webSocket(suburl, protocol.name) {
        safely {
            flow.collect {
                converter(it) ?.let { data ->
                    send(data)
                }
            }
        }
    }
}

fun <T> Route.includeWebsocketHandling(
    suburl: String,
    flow: Flow<T>,
    serializer: SerializationStrategy<T>,
    serialFormat: StandardKtorSerialFormat = standardKtorSerialFormat,
    protocol: URLProtocol = URLProtocol.WS,
    filter: (suspend WebSocketServerSession.(T) -> Boolean)? = null
) = includeWebsocketHandling(
    suburl,
    flow,
    protocol,
    converter = if (filter == null) {
        {
            serialFormat.encodeDefault(serializer, it)
        }
    } else {
        {
            if (filter(it)) {
                serialFormat.encodeDefault(serializer, it)
            } else {
                null
            }
        }
    }
)
