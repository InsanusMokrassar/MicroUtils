package dev.inmo.micro_utils.repos.ktor.client.key.value

import dev.inmo.micro_utils.ktor.common.*
import dev.inmo.micro_utils.repos.*
import io.ktor.client.HttpClient
import io.ktor.http.ContentType
import io.ktor.http.encodeURLQueryComponent
import kotlinx.serialization.*

class KtorKeyValueRepoClient<Key, Value> (
    readDelegate: ReadKeyValueRepo<Key, Value>,
    writeDelegate: WriteKeyValueRepo<Key, Value>
) : KeyValueRepo<Key, Value> by DelegateBasedKeyValueRepo(
    readDelegate,
    writeDelegate
) {
    companion object {
        inline operator fun <reified Key, reified Value> invoke(
            baseUrl: String,
            httpClient: HttpClient,
            contentType: ContentType,
            noinline idSerializer: suspend (Key) -> String,
            noinline valueSerializer: suspend (Value) -> String
        ) = KtorKeyValueRepoClient(
            KtorReadKeyValueRepoClient(
                baseUrl, httpClient, contentType, idSerializer, valueSerializer
            ),
            KtorWriteKeyValueRepoClient(
                baseUrl,
                httpClient,
                contentType
            )
        )
        inline operator fun <reified Key, reified Value> invoke(
            baseUrl: String,
            subpart: String,
            httpClient: HttpClient,
            contentType: ContentType,
            noinline idSerializer: suspend (Key) -> String,
            noinline valueSerializer: suspend (Value) -> String
        ) = KtorKeyValueRepoClient(
            buildStandardUrl(baseUrl, subpart),
            httpClient,
            contentType,
            idSerializer,
            valueSerializer
        )
    }
}

inline fun <reified Key, reified Value> KtorKeyValueRepoClient(
    baseUrl: String,
    httpClient: HttpClient,
    contentType: ContentType,
    idSerializer: SerializationStrategy<Key>,
    valueSerializer: SerializationStrategy<Value>,
    serialFormat: StringFormat,
) = KtorKeyValueRepoClient<Key, Value>(
    baseUrl,
    httpClient,
    contentType,
    {
        serialFormat.encodeToString(idSerializer, it).encodeURLQueryComponent()
    }
) {
    serialFormat.encodeToString(valueSerializer, it).encodeURLQueryComponent()
}

inline fun <reified Key, reified Value> KtorKeyValueRepoClient(
    baseUrl: String,
    httpClient: HttpClient,
    contentType: ContentType,
    idSerializer: SerializationStrategy<Key>,
    valueSerializer: SerializationStrategy<Value>,
    serialFormat: BinaryFormat,
) = KtorKeyValueRepoClient<Key, Value>(
    baseUrl,
    httpClient,
    contentType,
    {
        serialFormat.encodeHex(idSerializer, it)
    }
) {
    serialFormat.encodeHex(valueSerializer, it)
}
