package dev.inmo.micro_utils.repos.ktor.client.key_value

import dev.inmo.micro_utils.ktor.common.*
import dev.inmo.micro_utils.repos.*
import io.ktor.client.HttpClient
import io.ktor.http.ContentType
import io.ktor.http.encodeURLQueryComponent
import kotlinx.serialization.*

class KtorStandardKeyValueRepoClient<Key, Value> (
    readDelegate: ReadStandardKeyValueRepo<Key, Value>,
    writeDelegate: WriteStandardKeyValueRepo<Key, Value>
) : StandardKeyValueRepo<Key, Value> by DelegateBasedStandardKeyValueRepo(
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
        ) = KtorStandardKeyValueRepoClient(
            KtorReadStandardKeyValueRepoClient(
                baseUrl, httpClient, contentType, idSerializer, valueSerializer
            ),
            KtorWriteStandardKeyValueRepoClient(
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
        ) = KtorStandardKeyValueRepoClient(
            buildStandardUrl(baseUrl, subpart),
            httpClient,
            contentType,
            idSerializer,
            valueSerializer
        )
    }
}

inline fun <reified Key, reified Value> KtorStandardKeyValueRepoClient(
    baseUrl: String,
    httpClient: HttpClient,
    contentType: ContentType,
    idSerializer: SerializationStrategy<Key>,
    valueSerializer: SerializationStrategy<Value>,
    serialFormat: StringFormat,
) = KtorStandardKeyValueRepoClient<Key, Value>(
    baseUrl,
    httpClient,
    contentType,
    {
        serialFormat.encodeToString(idSerializer, it).encodeURLQueryComponent()
    }
) {
    serialFormat.encodeToString(valueSerializer, it).encodeURLQueryComponent()
}

inline fun <reified Key, reified Value> KtorStandardKeyValueRepoClient(
    baseUrl: String,
    httpClient: HttpClient,
    contentType: ContentType,
    idSerializer: SerializationStrategy<Key>,
    valueSerializer: SerializationStrategy<Value>,
    serialFormat: BinaryFormat,
) = KtorStandardKeyValueRepoClient<Key, Value>(
    baseUrl,
    httpClient,
    contentType,
    {
        serialFormat.encodeHex(idSerializer, it)
    }
) {
    serialFormat.encodeHex(valueSerializer, it)
}
