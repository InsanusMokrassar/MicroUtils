package dev.inmo.micro_utils.ktor.client

import dev.inmo.micro_utils.ktor.common.*
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import kotlinx.serialization.*

typealias BodyPair<T> = Pair<SerializationStrategy<T>, T>

class UnifiedRequester(
    val client: HttpClient = HttpClient(),
    val serialFormat: StandardKtorSerialFormat = standardKtorSerialFormat
) {
    suspend fun <ResultType> uniget(
        url: String,
        resultDeserializer: DeserializationStrategy<ResultType>
    ): ResultType = client.uniget(url, resultDeserializer, serialFormat)

    fun <T> encodeUrlQueryValue(
        serializationStrategy: SerializationStrategy<T>,
        value: T
    ) = serializationStrategy.encodeUrlQueryValue(
        value,
        serialFormat
    )

    suspend fun <BodyType, ResultType> unipost(
        url: String,
        bodyInfo: BodyPair<BodyType>,
        resultDeserializer: DeserializationStrategy<ResultType>
    ) = client.unipost(url, bodyInfo, resultDeserializer, serialFormat)

    fun <T> createStandardWebsocketFlow(
        url: String,
        checkReconnection: (Throwable?) -> Boolean = { true },
        deserializer: DeserializationStrategy<T>
    ) = client.createStandardWebsocketFlow(url, checkReconnection, deserializer, serialFormat)
}

val defaultRequester = UnifiedRequester()

suspend fun <ResultType> HttpClient.uniget(
    url: String,
    resultDeserializer: DeserializationStrategy<ResultType>,
    serialFormat: StandardKtorSerialFormat = standardKtorSerialFormat
) = get<StandardKtorSerialInputData>(
    url
).let {
    serialFormat.decodeDefault(resultDeserializer, it)
}


fun <T> SerializationStrategy<T>.encodeUrlQueryValue(
    value: T,
    serialFormat: StandardKtorSerialFormat = standardKtorSerialFormat
) = serialFormat.encodeHex(
    this,
    value
)

suspend fun <BodyType, ResultType> HttpClient.unipost(
    url: String,
    bodyInfo: BodyPair<BodyType>,
    resultDeserializer: DeserializationStrategy<ResultType>,
    serialFormat: StandardKtorSerialFormat = standardKtorSerialFormat
) = post<StandardKtorSerialInputData>(url) {
    body = serialFormat.encodeDefault(bodyInfo.first, bodyInfo.second)
}.let {
    serialFormat.decodeDefault(resultDeserializer, it)
}
