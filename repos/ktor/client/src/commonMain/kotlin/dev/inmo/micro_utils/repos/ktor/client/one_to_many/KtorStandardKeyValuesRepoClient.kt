package dev.inmo.micro_utils.repos.ktor.client.one_to_many

import dev.inmo.micro_utils.ktor.common.*
import dev.inmo.micro_utils.repos.*
import io.ktor.client.HttpClient
import io.ktor.http.ContentType
import io.ktor.http.encodeURLQueryComponent
import kotlinx.serialization.*

class KtorStandardKeyValuesRepoClient<Key, Value> (
    readDelegate: ReadOneToManyKeyValueRepo<Key, Value>,
    writeDelegate: WriteOneToManyKeyValueRepo<Key, Value>
) : OneToManyKeyValueRepo<Key, Value> by DelegateBasedOneToManyKeyValueRepo(
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
        ) = KtorStandardKeyValuesRepoClient(
            KtorReadStandardKeyValuesRepoClient(
                baseUrl,
                httpClient,
                contentType,
                keySerializer,
                valueSerializer
            ),
            KtorWriteStandardKeyValuesRepoClient(
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
        ) = KtorStandardKeyValuesRepoClient(
            buildStandardUrl(baseUrl, subpart),
            httpClient,
            contentType,
            keySerializer,
            valueSerializer
        )
    }
}

inline fun <reified Key : Any, reified Value : Any> KtorStandardKeyValuesRepoClient(
    baseUrl: String,
    httpClient: HttpClient,
    contentType: ContentType,
    keySerializer: SerializationStrategy<Key>,
    valueSerializer: SerializationStrategy<Value>,
    serialFormat: StringFormat,
) = KtorStandardKeyValuesRepoClient<Key, Value>(
    baseUrl,
    httpClient,
    contentType,
    {
        serialFormat.encodeToString(keySerializer, it).encodeURLQueryComponent()
    }
) {
    serialFormat.encodeToString(valueSerializer, it).encodeURLQueryComponent()
}

inline fun <reified Key : Any, reified Value : Any> KtorStandardKeyValuesRepoClient(
    baseUrl: String,
    httpClient: HttpClient,
    contentType: ContentType,
    keySerializer: SerializationStrategy<Key>,
    valueSerializer: SerializationStrategy<Value>,
    serialFormat: BinaryFormat,
) = KtorStandardKeyValuesRepoClient<Key, Value>(
    baseUrl,
    httpClient,
    contentType,
    {
        serialFormat.encodeHex(keySerializer, it)
    }
) {
    serialFormat.encodeHex(valueSerializer, it)
}
