package dev.inmo.micro_utils.repos.ktor.client.key.values

import dev.inmo.micro_utils.ktor.client.createStandardWebsocketFlow
import dev.inmo.micro_utils.ktor.common.*
import dev.inmo.micro_utils.repos.*
import dev.inmo.micro_utils.repos.ktor.common.one_to_many.onDataClearedRoute
import dev.inmo.micro_utils.repos.ktor.common.one_to_many.onNewValueRoute
import dev.inmo.micro_utils.repos.ktor.common.one_to_many.onValueRemovedRoute
import io.ktor.client.HttpClient
import io.ktor.http.ContentType
import io.ktor.http.encodeURLQueryComponent
import kotlinx.coroutines.flow.Flow
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
            onNewValue: Flow<Pair<Key, Value>> = httpClient.createStandardWebsocketFlow(
                buildStandardUrl(baseUrl, onNewValueRoute),
            ),
            onValueRemoved: Flow<Pair<Key, Value>> = httpClient.createStandardWebsocketFlow(
                buildStandardUrl(baseUrl, onValueRemovedRoute),
            ),
            onDataCleared: Flow<Key> = httpClient.createStandardWebsocketFlow(
                buildStandardUrl(baseUrl, onDataClearedRoute),
            ),
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
                contentType,
                onNewValue,
                onValueRemoved,
                onDataCleared
            )
        )
        inline operator fun <reified Key : Any, reified Value : Any> invoke(
            baseUrl: String,
            subpart: String,
            httpClient: HttpClient,
            contentType: ContentType,
            noinline keySerializer: suspend (Key) -> String,
            onNewValue: Flow<Pair<Key, Value>> = httpClient.createStandardWebsocketFlow(
                buildStandardUrl(baseUrl, onNewValueRoute),
            ),
            onValueRemoved: Flow<Pair<Key, Value>> = httpClient.createStandardWebsocketFlow(
                buildStandardUrl(baseUrl, onValueRemovedRoute),
            ),
            onDataCleared: Flow<Key> = httpClient.createStandardWebsocketFlow(
                buildStandardUrl(baseUrl, onDataClearedRoute),
            ),
            noinline valueSerializer: suspend (Value) -> String
        ) = KtorKeyValuesRepoClient(
            buildStandardUrl(baseUrl, subpart),
            httpClient,
            contentType,
            keySerializer,
            onNewValue,
            onValueRemoved,
            onDataCleared,
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
    onNewValue: Flow<Pair<Key, Value>> = httpClient.createStandardWebsocketFlow(
        buildStandardUrl(baseUrl, onNewValueRoute),
    ),
    onValueRemoved: Flow<Pair<Key, Value>> = httpClient.createStandardWebsocketFlow(
        buildStandardUrl(baseUrl, onValueRemovedRoute),
    ),
    onDataCleared: Flow<Key> = httpClient.createStandardWebsocketFlow(
        buildStandardUrl(baseUrl, onDataClearedRoute),
    ),
) = KtorKeyValuesRepoClient<Key, Value>(
    baseUrl,
    httpClient,
    contentType,
    {
        serialFormat.encodeToString(keySerializer, it).encodeURLQueryComponent()
    },
    onNewValue,
    onValueRemoved,
    onDataCleared
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
    onNewValue: Flow<Pair<Key, Value>> = httpClient.createStandardWebsocketFlow(
        buildStandardUrl(baseUrl, onNewValueRoute),
    ),
    onValueRemoved: Flow<Pair<Key, Value>> = httpClient.createStandardWebsocketFlow(
        buildStandardUrl(baseUrl, onValueRemovedRoute),
    ),
    onDataCleared: Flow<Key> = httpClient.createStandardWebsocketFlow(
        buildStandardUrl(baseUrl, onDataClearedRoute),
    ),
) = KtorKeyValuesRepoClient<Key, Value>(
    baseUrl,
    httpClient,
    contentType,
    {
        serialFormat.encodeHex(keySerializer, it)
    },
    onNewValue,
    onValueRemoved,
    onDataCleared
) {
    serialFormat.encodeHex(valueSerializer, it)
}
