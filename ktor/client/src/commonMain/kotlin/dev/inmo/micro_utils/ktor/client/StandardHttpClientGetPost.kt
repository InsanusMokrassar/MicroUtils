package dev.inmo.micro_utils.ktor.client

import dev.inmo.micro_utils.ktor.common.standardKtorSerialFormat
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import kotlinx.serialization.*

typealias BodyPair<T> = Pair<SerializationStrategy<T>, T>

suspend fun <ResultType> HttpClient.uniget(
    url: String,
    resultDeserializer: DeserializationStrategy<ResultType>
) = get<ByteArray>(
    url
).let {
    standardKtorSerialFormat.decodeFromByteArray(resultDeserializer, it)
}

fun <T> SerializationStrategy<T>.encodeUrlQueryValue(value: T) = standardKtorSerialFormat.encodeToHexString(
    this,
    value
)

suspend fun <BodyType, ResultType> HttpClient.unipost(
    url: String,
    bodyInfo: BodyPair<BodyType>,
    resultDeserializer: DeserializationStrategy<ResultType>
) = post<ByteArray>(url) {
    body = standardKtorSerialFormat.encodeToByteArray(bodyInfo.first, bodyInfo.second)
}.let {
    standardKtorSerialFormat.decodeFromByteArray(resultDeserializer, it)
}
