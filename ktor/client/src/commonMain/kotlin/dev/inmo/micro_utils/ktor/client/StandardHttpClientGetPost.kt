package dev.inmo.micro_utils.ktor.client

import dev.inmo.micro_utils.common.MPPFile
import dev.inmo.micro_utils.common.filename
import dev.inmo.micro_utils.ktor.common.*
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.readBytes
import io.ktor.http.*
import io.ktor.utils.io.core.ByteReadPacket
import kotlinx.serialization.*

@Deprecated("This class will be removed in next")
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
        bodyInfo: Pair<SerializationStrategy<BodyType>, BodyType>,
        resultDeserializer: DeserializationStrategy<ResultType>
    ) = client.unipost(url, bodyInfo, resultDeserializer, serialFormat)

    suspend fun <ResultType> unimultipart(
        url: String,
        filename: String,
        inputProvider: InputProvider,
        resultDeserializer: DeserializationStrategy<ResultType>,
        mimetype: String = "*/*",
        additionalParametersBuilder: FormBuilder.() -> Unit = {},
        dataHeadersBuilder: HeadersBuilder.() -> Unit = {},
        requestBuilder: HttpRequestBuilder.() -> Unit = {},
    ): ResultType = client.unimultipart(url, filename, inputProvider, resultDeserializer, mimetype, additionalParametersBuilder, dataHeadersBuilder, requestBuilder, serialFormat)

    suspend fun <BodyType, ResultType> unimultipart(
        url: String,
        filename: String,
        inputProvider: InputProvider,
        otherData: Pair<SerializationStrategy<BodyType>, BodyType>,
        resultDeserializer: DeserializationStrategy<ResultType>,
        mimetype: String = "*/*",
        additionalParametersBuilder: FormBuilder.() -> Unit = {},
        dataHeadersBuilder: HeadersBuilder.() -> Unit = {},
        requestBuilder: HttpRequestBuilder.() -> Unit = {},
    ): ResultType = client.unimultipart(url, filename, otherData, inputProvider, resultDeserializer, mimetype, additionalParametersBuilder, dataHeadersBuilder, requestBuilder, serialFormat)

    suspend fun <ResultType> unimultipart(
        url: String,
        mppFile: MPPFile,
        resultDeserializer: DeserializationStrategy<ResultType>,
        mimetype: String = "*/*",
        additionalParametersBuilder: FormBuilder.() -> Unit = {},
        dataHeadersBuilder: HeadersBuilder.() -> Unit = {},
        requestBuilder: HttpRequestBuilder.() -> Unit = {}
    ): ResultType = client.unimultipart(
        url, mppFile, resultDeserializer, mimetype, additionalParametersBuilder, dataHeadersBuilder, requestBuilder, serialFormat
    )

    suspend fun <BodyType, ResultType> unimultipart(
        url: String,
        mppFile: MPPFile,
        otherData: Pair<SerializationStrategy<BodyType>, BodyType>,
        resultDeserializer: DeserializationStrategy<ResultType>,
        mimetype: String = "*/*",
        additionalParametersBuilder: FormBuilder.() -> Unit = {},
        dataHeadersBuilder: HeadersBuilder.() -> Unit = {},
        requestBuilder: HttpRequestBuilder.() -> Unit = {}
    ): ResultType = client.unimultipart(
        url, mppFile, otherData, resultDeserializer, mimetype, additionalParametersBuilder, dataHeadersBuilder, requestBuilder, serialFormat
    )

    fun <T> createStandardWebsocketFlow(
        url: String,
        checkReconnection: suspend (Throwable?) -> Boolean,
        deserializer: DeserializationStrategy<T>,
        requestBuilder: HttpRequestBuilder.() -> Unit = {},
    ) = client.createStandardWebsocketFlow(url, deserializer, checkReconnection, serialFormat, requestBuilder)

    fun <T> createStandardWebsocketFlow(
        url: String,
        deserializer: DeserializationStrategy<T>,
        requestBuilder: HttpRequestBuilder.() -> Unit = {},
    ) = createStandardWebsocketFlow(url, { true }, deserializer, requestBuilder)
}

val defaultRequester = UnifiedRequester()

suspend fun <ResultType> HttpClient.uniget(
    url: String,
    resultDeserializer: DeserializationStrategy<ResultType>,
    serialFormat: StandardKtorSerialFormat = standardKtorSerialFormat
) = get(url).let {
    serialFormat.decodeDefault(resultDeserializer, it.body<StandardKtorSerialInputData>())
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
    bodyInfo: Pair<SerializationStrategy<BodyType>, BodyType>,
    resultDeserializer: DeserializationStrategy<ResultType>,
    serialFormat: StandardKtorSerialFormat = standardKtorSerialFormat
) = post(url) {
    setBody(
        serialFormat.encodeDefault(bodyInfo.first, bodyInfo.second)
    )
}.let {
    serialFormat.decodeDefault(resultDeserializer, it.body<StandardKtorSerialInputData>())
}

suspend fun <ResultType> HttpClient.unimultipart(
    url: String,
    filename: String,
    inputProvider: InputProvider,
    resultDeserializer: DeserializationStrategy<ResultType>,
    mimetype: String = "*/*",
    additionalParametersBuilder: FormBuilder.() -> Unit = {},
    dataHeadersBuilder: HeadersBuilder.() -> Unit = {},
    requestBuilder: HttpRequestBuilder.() -> Unit = {},
    serialFormat: StandardKtorSerialFormat = standardKtorSerialFormat
): ResultType = submitFormWithBinaryData(
    url,
    formData = formData {
        append(
            "bytes",
            inputProvider,
            Headers.build {
                append(HttpHeaders.ContentType, mimetype)
                append(HttpHeaders.ContentDisposition, "filename=\"$filename\"")
                dataHeadersBuilder()
            }
        )
        additionalParametersBuilder()
    }
) {
    requestBuilder()
}.let { serialFormat.decodeDefault(resultDeserializer, it.body<StandardKtorSerialInputData>()) }

suspend fun <BodyType, ResultType> HttpClient.unimultipart(
    url: String,
    filename: String,
    otherData: Pair<SerializationStrategy<BodyType>, BodyType>,
    inputProvider: InputProvider,
    resultDeserializer: DeserializationStrategy<ResultType>,
    mimetype: String = "*/*",
    additionalParametersBuilder: FormBuilder.() -> Unit = {},
    dataHeadersBuilder: HeadersBuilder.() -> Unit = {},
    requestBuilder: HttpRequestBuilder.() -> Unit = {},
    serialFormat: StandardKtorSerialFormat = standardKtorSerialFormat
): ResultType = unimultipart(
    url,
    filename,
    inputProvider,
    resultDeserializer,
    mimetype,
    additionalParametersBuilder = {
        val serialized = serialFormat.encodeDefault(otherData.first, otherData.second)
        append(
            "data",
            InputProvider(serialized.size.toLong()) {
                ByteReadPacket(serialized)
            },
            Headers.build {
                append(HttpHeaders.ContentType, ContentType.Application.Cbor.contentType)
                append(HttpHeaders.ContentDisposition, "filename=data.bytes")
                dataHeadersBuilder()
            }
        )
        additionalParametersBuilder()
    },
    dataHeadersBuilder,
    requestBuilder,
    serialFormat
)

suspend fun <ResultType> HttpClient.unimultipart(
    url: String,
    mppFile: MPPFile,
    resultDeserializer: DeserializationStrategy<ResultType>,
    mimetype: String = "*/*",
    additionalParametersBuilder: FormBuilder.() -> Unit = {},
    dataHeadersBuilder: HeadersBuilder.() -> Unit = {},
    requestBuilder: HttpRequestBuilder.() -> Unit = {},
    serialFormat: StandardKtorSerialFormat = standardKtorSerialFormat
): ResultType = unimultipart(
    url,
    mppFile.filename.string,
    mppFile.inputProvider(),
    resultDeserializer,
    mimetype,
    additionalParametersBuilder,
    dataHeadersBuilder,
    requestBuilder,
    serialFormat
)

suspend fun <BodyType, ResultType> HttpClient.unimultipart(
    url: String,
    mppFile: MPPFile,
    otherData: Pair<SerializationStrategy<BodyType>, BodyType>,
    resultDeserializer: DeserializationStrategy<ResultType>,
    mimetype: String = "*/*",
    additionalParametersBuilder: FormBuilder.() -> Unit = {},
    dataHeadersBuilder: HeadersBuilder.() -> Unit = {},
    requestBuilder: HttpRequestBuilder.() -> Unit = {},
    serialFormat: StandardKtorSerialFormat = standardKtorSerialFormat
): ResultType = unimultipart(
    url,
    mppFile,
    resultDeserializer,
    mimetype,
    additionalParametersBuilder = {
        val serialized = serialFormat.encodeDefault(otherData.first, otherData.second)
        append(
            "data",
            InputProvider(serialized.size.toLong()) {
                ByteReadPacket(serialized)
            },
            Headers.build {
                append(HttpHeaders.ContentType, ContentType.Application.Cbor.contentType)
                append(HttpHeaders.ContentDisposition, "filename=data.bytes")
                dataHeadersBuilder()
            }
        )
        additionalParametersBuilder()
    },
    dataHeadersBuilder,
    requestBuilder,
    serialFormat
)
