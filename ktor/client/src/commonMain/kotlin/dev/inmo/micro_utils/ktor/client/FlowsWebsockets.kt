package dev.inmo.micro_utils.ktor.client

import dev.inmo.micro_utils.coroutines.safely
import dev.inmo.micro_utils.ktor.common.*
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.ws
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.websocket.Frame
import io.ktor.websocket.readBytes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.serialization.DeserializationStrategy

/**
 * @param checkReconnection This lambda will be called when it is required to reconnect to websocket to establish
 * connection. Must return true in case if must be reconnected. By default always reconnecting
 */
inline fun <T> HttpClient.createStandardWebsocketFlow(
    url: String,
    crossinline checkReconnection: (Throwable?) -> Boolean = { true },
    noinline requestBuilder: HttpRequestBuilder.() -> Unit = {},
    crossinline conversation: suspend (StandardKtorSerialInputData) -> T
): Flow<T> {
    val correctedUrl = url.asCorrectWebSocketUrl

    return channelFlow {
        val producerScope = this@channelFlow
        do {
            val reconnect = try {
                safely {
                    ws(correctedUrl, requestBuilder) {
                        for (received in incoming) {
                            when (received) {
                                is Frame.Binary -> producerScope.send(conversation(received.readBytes()))
                                else -> {
                                    producerScope.close()
                                    return@ws
                                }
                            }
                        }
                    }
                }
                checkReconnection(null)
            } catch (e: Throwable) {
                checkReconnection(e).also {
                    if (!it) {
                        producerScope.close(e)
                    }
                }
            }
        } while (reconnect)
        if (!producerScope.isClosedForSend) {
            safely(
                { it.printStackTrace() }
            ) {
                producerScope.close()
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
    crossinline checkReconnection: (Throwable?) -> Boolean = { true },
    serialFormat: StandardKtorSerialFormat = standardKtorSerialFormat,
    noinline requestBuilder: HttpRequestBuilder.() -> Unit = {},
) = createStandardWebsocketFlow(
    url,
    checkReconnection,
    requestBuilder
) {
    serialFormat.decodeDefault(deserializer, it)
}
