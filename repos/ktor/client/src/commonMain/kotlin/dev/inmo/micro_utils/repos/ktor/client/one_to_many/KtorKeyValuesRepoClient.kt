package dev.inmo.micro_utils.repos.ktor.client.one_to_many

import dev.inmo.micro_utils.ktor.common.*
import dev.inmo.micro_utils.repos.*
import io.ktor.client.HttpClient
import io.ktor.http.ContentType
import io.ktor.http.encodeURLQueryComponent
import kotlinx.serialization.*

class KtorKeyValuesRepoClient<Key, Value> (
    readDelegate: ReadKeyValuesRepo<Key, Value>,
    writeDelegate: WriteKeyValuesRepo<Key, Value>
) : KeyValuesRepo<Key, Value> by DelegateBasedKeyValuesRepo(
    readDelegate,
    writeDelegate
) {
    companion object {
        inline operator fun <reified Key : Any, reified Value : Any> invoke(
            baseUrl: String,
            httpClient: HttpClient,
            contentType: ContentType,
            noinline keySerializer: suspend (Key) -> String,
            noinline valueSerializer: suspend (Value) -> String
        ) = KtorKeyValuesRepoClient(
            KtorReadKeyValuesRepoClient(
                baseUrl,
                httpClient,
                contentType,
                keySerializer,
                valueSerializer
            ),
            KtorWriteKeyValuesRepoClient(
                baseUrl,
                httpClient,
                contentType
            )
        )
        inline operator fun <reified Key : Any, reified Value : Any> invoke(
            baseUrl: String,
            subpart: String,
            httpClient: HttpClient,
            contentType: ContentType,
            noinline keySerializer: suspend (Key) -> String,
            noinline valueSerializer: suspend (Value) -> String
        ) = KtorKeyValuesRepoClient(
            buildStandardUrl(baseUrl, subpart),
            httpClient,
            contentType,
            keySerializer,
            valueSerializer
        )
    }
}

inline fun <reified Key : Any, reified Value : Any> KtorKeyValuesRepoClient(
    baseUrl: String,
    httpClient: HttpClient,
    contentType: ContentType,
    keySerializer: SerializationStrategy<Key>,
    valueSerializer: SerializationStrategy<Value>,
    serialFormat: StringFormat,
) = KtorKeyValuesRepoClient<Key, Value>(
    baseUrl,
    httpClient,
    contentType,
    {
        serialFormat.encodeToString(keySerializer, it).encodeURLQueryComponent()
    }
) {
    serialFormat.encodeToString(valueSerializer, it).encodeURLQueryComponent()
}

inline fun <reified Key : Any, reified Value : Any> KtorKeyValuesRepoClient(
    baseUrl: String,
    httpClient: HttpClient,
    contentType: ContentType,
    keySerializer: SerializationStrategy<Key>,
    valueSerializer: SerializationStrategy<Value>,
    serialFormat: BinaryFormat,
) = KtorKeyValuesRepoClient<Key, Value>(
    baseUrl,
    httpClient,
    contentType,
    {
        serialFormat.encodeHex(keySerializer, it)
    }
) {
    serialFormat.encodeHex(valueSerializer, it)
}
