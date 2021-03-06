package dev.inmo.micro_utils.ktor.server

import dev.inmo.micro_utils.coroutines.safely
import dev.inmo.micro_utils.ktor.common.*
import io.ktor.http.cio.websocket.*
import io.ktor.routing.Route
import io.ktor.websocket.webSocket
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.serialization.SerializationStrategy

private suspend fun DefaultWebSocketSession.checkReceivedAndCloseIfExists() {
    if (incoming.tryReceive() != null) {
        close()
        throw CorrectCloseException
    }
}

fun <T> Route.includeWebsocketHandling(
    suburl: String,
    flow: Flow<T>,
    converter: (T) -> StandardKtorSerialInputData
) {
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
