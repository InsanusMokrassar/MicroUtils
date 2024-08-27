package dev.inmo.micro_utils.ktor.server

import dev.inmo.micro_utils.coroutines.safely
import dev.inmo.micro_utils.ktor.common.*
import io.ktor.http.URLProtocol
import io.ktor.server.application.install
import io.ktor.server.application.pluginOrNull
import io.ktor.server.routing.Routing
import io.ktor.server.routing.application
import io.ktor.server.websocket.*
import io.ktor.websocket.send
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.SerializationStrategy

inline fun <reified T : Any> Routing.includeWebsocketHandling(
    suburl: String,
    flow: Flow<T>,
    protocol: URLProtocol? = null,
    noinline dataMapper: suspend WebSocketServerSession.(T) -> T? = { it }
) {
    application.apply {
        pluginOrNull(WebSockets) ?: install(WebSockets)
    }
    webSocket(suburl, protocol ?.name) {
        safely {
            flow.collect {
                sendSerialized(dataMapper(it) ?: return@collect)
            }
        }
    }
}
