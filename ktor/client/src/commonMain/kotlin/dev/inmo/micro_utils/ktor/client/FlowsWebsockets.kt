package dev.inmo.micro_utils.ktor.client

import dev.inmo.micro_utils.coroutines.runCatchingSafely
import dev.inmo.micro_utils.coroutines.safely
import dev.inmo.micro_utils.ktor.common.*
import io.ktor.client.HttpClient
import io.ktor.client.plugins.pluginOrNull
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.ws
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.websocket.Frame
import io.ktor.websocket.readBytes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.isActive
import kotlinx.serialization.DeserializationStrategy

/**
 * @param checkReconnection This lambda will be called when it is required to reconnect to websocket to establish
 * connection. Must return true in case if must be reconnected. By default always reconnecting
 */
inline fun <T> HttpClient.createStandardWebsocketFlow(
    url: String,
    crossinline checkReconnection: suspend (Throwable?) -> Boolean = { true },
    noinline requestBuilder: HttpRequestBuilder.() -> Unit = {},
    crossinline conversation: suspend (StandardKtorSerialInputData) -> T
): Flow<T> {
    pluginOrNull(WebSockets) ?: error("Plugin $WebSockets must be installed for using createStandardWebsocketFlow")

    val correctedUrl = url.asCorrectWebSocketUrl

    return channelFlow {
        do {
            val reconnect = runCatchingSafely {
                ws(correctedUrl, requestBuilder) {
                    for (received in incoming) {
                        when (received) {
                            is Frame.Binary -> send(conversation(received.data))
                            else -> {
                                close()
                                return@ws
                            }
                        }
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

/**
 * @param checkReconnection This lambda will be called when it is required to reconnect to websocket to establish
 * connection. Must return true in case if must be reconnected. By default always reconnecting
 */
inline fun <T> HttpClient.createStandardWebsocketFlow(
    url: String,
    deserializer: DeserializationStrategy<T>,
    crossinline checkReconnection: suspend (Throwable?) -> Boolean = { true },
    serialFormat: StandardKtorSerialFormat = standardKtorSerialFormat,
    noinline requestBuilder: HttpRequestBuilder.() -> Unit = {},
) = createStandardWebsocketFlow(
    url,
    checkReconnection,
    requestBuilder
) {
    serialFormat.decodeDefault(deserializer, it)
}
