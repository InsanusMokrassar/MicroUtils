package dev.inmo.micro_utils.ktor.client

import dev.inmo.micro_utils.coroutines.runCatchingSafely
import dev.inmo.micro_utils.coroutines.safely
import dev.inmo.micro_utils.ktor.common.*
import io.ktor.client.HttpClient
import io.ktor.client.plugins.pluginOrNull
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.websocket.Frame
import io.ktor.websocket.readBytes
import io.ktor.websocket.serialization.sendSerializedBase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.isActive
import kotlinx.serialization.DeserializationStrategy

/**
 * @param checkReconnection This lambda will be called when it is required to reconnect to websocket to establish
 * connection. Must return true in case if must be reconnected. By default always reconnecting
 */
inline fun <reified T> HttpClient.createStandardWebsocketFlow(
    url: String,
    noinline checkReconnection: suspend (Throwable?) -> Boolean = { true },
    noinline requestBuilder: HttpRequestBuilder.() -> Unit = {}
): Flow<T> {
    pluginOrNull(WebSockets) ?: error("Plugin $WebSockets must be installed for using createStandardWebsocketFlow")

    val correctedUrl = url.asCorrectWebSocketUrl

    return channelFlow {
        do {
            val reconnect = runCatchingSafely {
                ws(correctedUrl, requestBuilder) {
                    for (received in incoming) {
                        sendSerialized(received.data)
                    }
                }
                checkReconnection(null)
            }.getOrElse { e ->
                checkReconnection(e).also {
                    if (!it) {
                        close(e)
                    }
                }
            }
        } while (reconnect && isActive)

        if (isActive) {
            safely {
                close()
            }
        }
    }
}