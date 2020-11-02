package dev.inmo.micro_utils.ktor.client

import dev.inmo.micro_utils.ktor.common.*
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy

typealias BodyPair<T> = Pair<SerializationStrategy<T>, T>

suspend fun <ResultType> HttpClient.uniget(
    url: String,
    resultDeserializer: DeserializationStrategy<ResultType>
) = get<StandardKtorSerialInputData>(
    url
).let {
    standardKtorSerialFormat.decodeDefault(resultDeserializer, it)
}


fun <T> SerializationStrategy<T>.encodeUrlQueryValue(value: T) = standardKtorSerialFormat.encodeHex(
    this,
    value
)

suspend fun <BodyType, ResultType> HttpClient.unipost(
    url: String,
    bodyInfo: BodyPair<BodyType>,
    resultDeserializer: DeserializationStrategy<ResultType>
) = post<StandardKtorSerialInputData>(url) {
    body = standardKtorSerialFormat.encodeDefault(bodyInfo.first, bodyInfo.second)
}.let {
    standardKtorSerialFormat.decodeDefault(resultDeserializer, it)
}
