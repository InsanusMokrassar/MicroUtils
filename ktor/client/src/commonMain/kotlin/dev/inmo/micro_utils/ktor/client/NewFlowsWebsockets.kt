package dev.inmo.micro_utils.ktor.client

import dev.inmo.micro_utils.common.Warning
import dev.inmo.micro_utils.coroutines.runCatchingSafely
import dev.inmo.micro_utils.coroutines.safely
import dev.inmo.micro_utils.ktor.common.*
import io.ktor.client.HttpClient
import io.ktor.client.plugins.pluginOrNull
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.URLProtocol
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.isActive

/**
 * @param checkReconnection This lambda will be called when it is required to reconnect to websocket to establish
 * connection. Must return true in case if must be reconnected. By default always reconnecting
 */
@Warning("This feature is internal and should not be used directly. It is can be changed without any notification and warranty on compile-time or other guaranties")
inline fun <reified T : Any> openBaseWebSocketFlow(
    noinline checkReconnection: suspend (Throwable?) -> Boolean = { true },
    noinline webSocketSessionRequest: suspend SendChannel<T>.() -> Unit
): Flow<T> {
    return channelFlow {
        do {
            val reconnect = runCatchingSafely {
                webSocketSessionRequest()
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
inline fun <reified T : Any> HttpClient.openWebSocketFlow(
    url: String,
    useSecureConnection: Boolean,
    noinline checkReconnection: suspend (Throwable?) -> Boolean = { true },
    noinline requestBuilder: HttpRequestBuilder.() -> Unit = {}
): Flow<T> {
    pluginOrNull(WebSockets) ?: error("Plugin $WebSockets must be installed for using createStandardWebsocketFlow")

    return openBaseWebSocketFlow<T>(checkReconnection) {
        val block: suspend DefaultClientWebSocketSession.() -> Unit = {
            while (isActive) {
                send(receiveDeserialized<T>())
            }
        }

        if (useSecureConnection) {
            wss(url, requestBuilder, block)
        } else {
            ws(url, requestBuilder, block)
        }
    }
}

/**
 * @param checkReconnection This lambda will be called when it is required to reconnect to websocket to establish
 * connection. Must return true in case if must be reconnected. By default always reconnecting
 */
inline fun <reified T : Any> HttpClient.openWebSocketFlow(
    url: String,
    noinline checkReconnection: suspend (Throwable?) -> Boolean = { true },
    noinline requestBuilder: HttpRequestBuilder.() -> Unit = {}
): Flow<T> = openWebSocketFlow(url, false, checkReconnection, requestBuilder)

/**
 * @param checkReconnection This lambda will be called when it is required to reconnect to websocket to establish
 * connection. Must return true in case if must be reconnected. By default always reconnecting
 */
inline fun <reified T : Any> HttpClient.openSecureWebSocketFlow(
    url: String,
    noinline checkReconnection: suspend (Throwable?) -> Boolean = { true },
    noinline requestBuilder: HttpRequestBuilder.() -> Unit = {}
): Flow<T> = openWebSocketFlow(url, true, checkReconnection, requestBuilder)

/**
 * @param checkReconnection This lambda will be called when it is required to reconnect to websocket to establish
 * connection. Must return true in case if must be reconnected. By default always reconnecting
 */
inline fun <reified T : Any> HttpClient.createStandardWebsocketFlow(
    url: String,
    noinline checkReconnection: suspend (Throwable?) -> Boolean = { true },
    noinline requestBuilder: HttpRequestBuilder.() -> Unit = {}
): Flow<T> = openWebSocketFlow(url, checkReconnection, requestBuilder)
