package dev.inmo.micro_utils.ktor.server

import dev.inmo.micro_utils.coroutines.safely
import dev.inmo.micro_utils.ktor.common.*
import io.ktor.http.URLProtocol
import io.ktor.server.application.install
import io.ktor.server.application.pluginOrNull
import io.ktor.server.routing.Route
import io.ktor.server.routing.application
import io.ktor.server.websocket.*
import io.ktor.websocket.send
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.SerializationStrategy

fun <T> Route.includeWebsocketHandling(
    suburl: String,
    flow: Flow<T>,
    protocol: URLProtocol = URLProtocol.WS,
    converter: suspend WebSocketServerSession.(T) -> StandardKtorSerialInputData?
) {
    application.apply {
        pluginOrNull(WebSockets) ?: install(WebSockets)
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
