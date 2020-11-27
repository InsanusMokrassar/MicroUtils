package dev.inmo.micro_utils.ktor.client

import dev.inmo.micro_utils.ktor.common.*
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import kotlinx.serialization.*

typealias BodyPair<T> = Pair<SerializationStrategy<T>, T>

class UnifiedRequester(
    private val client: HttpClient = HttpClient(),
    private val serialFormat: StandardKtorSerialFormat = standardKtorSerialFormat
) {
    suspend fun <ResultType> uniget(
        url: String,
        resultDeserializer: DeserializationStrategy<ResultType>
    ): ResultType = client.get<StandardKtorSerialInputData>(
        url
    ).let {
        serialFormat.decodeDefault(resultDeserializer, it)
    }


    fun <T> encodeUrlQueryValue(
        serializationStrategy: SerializationStrategy<T>,
        value: T
    ) = serialFormat.encodeHex(
        serializationStrategy,
        value
    )

    suspend fun <BodyType, ResultType> unipost(
        url: String,
        bodyInfo: BodyPair<BodyType>,
        resultDeserializer: DeserializationStrategy<ResultType>
    ) = client.post<StandardKtorSerialInputData>(url) {
        body = serialFormat.encodeDefault(bodyInfo.first, bodyInfo.second)
    }.let {
        serialFormat.decodeDefault(resultDeserializer, it)
    }

    fun <T> createStandardWebsocketFlow(
        url: String,
        checkReconnection: (Throwable?) -> Boolean = { true },
        deserializer: DeserializationStrategy<T>
    ) = client.createStandardWebsocketFlow(url, checkReconnection, deserializer, serialFormat)
}

val defaultRequester = UnifiedRequester()

suspend fun <ResultType> HttpClient.uniget(
    url: String,
    resultDeserializer: DeserializationStrategy<ResultType>
) = defaultRequester.uniget(url, resultDeserializer)


fun <T> SerializationStrategy<T>.encodeUrlQueryValue(value: T) = defaultRequester.encodeUrlQueryValue(this, value)

suspend fun <BodyType, ResultType> HttpClient.unipost(
    url: String,
    bodyInfo: BodyPair<BodyType>,
    resultDeserializer: DeserializationStrategy<ResultType>
) = defaultRequester.unipost(url, bodyInfo, resultDeserializer)
