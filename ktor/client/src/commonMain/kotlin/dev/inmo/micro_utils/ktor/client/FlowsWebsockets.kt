package dev.inmo.micro_utils.ktor.client

import dev.inmo.micro_utils.coroutines.safely
import dev.inmo.micro_utils.ktor.common.*
import io.ktor.client.HttpClient
import io.ktor.client.features.websocket.ws
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.serialization.DeserializationStrategy
import kotlin.js.JsExport

/**
 * @param checkReconnection This lambda will be called when it is required to reconnect to websocket to establish
 * connection. Must return true in case if must be reconnected. By default always reconnecting
 */
@JsExport
inline fun <T> HttpClient.createStandardWebsocketFlow(
    url: String,
    crossinline checkReconnection: (Throwable?) -> Boolean = { true },
    crossinline conversation: suspend (StandardKtorSerialInputData) -> T
): Flow<T> {
    val correctedUrl = url.asCorrectWebSocketUrl

    return channelFlow {
        val producerScope = this@channelFlow
        do {
            val reconnect = try {
                safely ({ throw it }) {
                    ws(correctedUrl) {
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
@JsExport
inline fun <T> HttpClient.createStandardWebsocketFlow(
    url: String,
    crossinline checkReconnection: (Throwable?) -> Boolean = { true },
    deserializer: DeserializationStrategy<T>
) = createStandardWebsocketFlow(
    url,
    checkReconnection
) {
    standardKtorSerialFormat.decodeDefault(deserializer, it)
}
