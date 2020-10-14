package dev.inmo.micro_utils.ktor.client

import dev.inmo.micro_utils.ktor.common.*
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import kotlinx.serialization.*
import kotlin.js.JsExport

typealias BodyPair<T> = Pair<SerializationStrategy<T>, T>

@JsExport
suspend fun <ResultType> HttpClient.uniget(
    url: String,
    resultDeserializer: DeserializationStrategy<ResultType>
) = get<StandardKtorSerialInputData>(
    url
).let {
    standardKtorSerialFormat.decodeDefault(resultDeserializer, it)
}


@JsExport
fun <T> SerializationStrategy<T>.encodeUrlQueryValue(value: T) = standardKtorSerialFormat.encodeHex(
    this,
    value
)

@JsExport
suspend fun <BodyType, ResultType> HttpClient.unipost(
    url: String,
    bodyInfo: BodyPair<BodyType>,
    resultDeserializer: DeserializationStrategy<ResultType>
) = post<StandardKtorSerialInputData>(url) {
    body = standardKtorSerialFormat.encodeDefault(bodyInfo.first, bodyInfo.second)
}.let {
    standardKtorSerialFormat.decodeDefault(resultDeserializer, it)
}
