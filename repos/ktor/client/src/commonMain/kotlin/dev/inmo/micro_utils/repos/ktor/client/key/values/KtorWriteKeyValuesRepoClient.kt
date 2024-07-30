package dev.inmo.micro_utils.repos.ktor.client.key.values

import dev.inmo.micro_utils.ktor.client.createStandardWebsocketFlow
import dev.inmo.micro_utils.ktor.client.throwOnUnsuccess
import dev.inmo.micro_utils.ktor.common.*
import dev.inmo.micro_utils.repos.WriteKeyValuesRepo
import dev.inmo.micro_utils.repos.ktor.common.one_to_many.*
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.http.*
import io.ktor.util.InternalAPI
import io.ktor.util.reflect.TypeInfo
import io.ktor.util.reflect.typeInfo
import kotlinx.coroutines.flow.Flow

class KtorWriteKeyValuesRepoClient<Key : Any, Value : Any>(
    private val baseUrl: String,
    private val httpClient: HttpClient,
    private val contentType: ContentType,
    override val onNewValue: Flow<Pair<Key, Value>>,
    override val onValueRemoved: Flow<Pair<Key, Value>>,
    override val onDataCleared: Flow<Key>,
    private val keyTypeInfo: TypeInfo,
    private val valueTypeInfo: TypeInfo,
    private val keyToValuesMapTypeInfo: TypeInfo
) : WriteKeyValuesRepo<Key, Value> {

    @OptIn(InternalAPI::class)
    override suspend fun add(toAdd: Map<Key, List<Value>>) {
        httpClient.post(
            buildStandardUrl(baseUrl, addRoute)
        ) {
            body = toAdd
            bodyType = keyToValuesMapTypeInfo
            contentType(contentType)
        }.throwOnUnsuccess { "Unable to add $toAdd" }
    }

    @OptIn(InternalAPI::class)
    override suspend fun remove(toRemove: Map<Key, List<Value>>) {
        httpClient.post(
            buildStandardUrl(baseUrl, removeRoute)
        ) {
            body = toRemove
            bodyType = keyToValuesMapTypeInfo
            contentType(contentType)
        }.throwOnUnsuccess { "Unable to remove $toRemove" }
    }

    @OptIn(InternalAPI::class)
    override suspend fun removeWithValue(v: Value) {
        httpClient.post(
            buildStandardUrl(baseUrl, removeWithValueRoute)
        ) {
            body = v
            bodyType = valueTypeInfo
            contentType(contentType)
        }.throwOnUnsuccess { "Unable to remove $v" }
    }

    @OptIn(InternalAPI::class)
    override suspend fun clear(k: Key) {
        httpClient.post(
            buildStandardUrl(baseUrl, clearRoute)
        ) {
            body = k
            bodyType = keyTypeInfo
            contentType(contentType)
        }.throwOnUnsuccess { "Unable to clear data with key $k" }
    }

    @OptIn(InternalAPI::class)
    override suspend fun clearWithValue(v: Value) {
        httpClient.post(
            buildStandardUrl(baseUrl, clearWithValueRoute)
        ) {
            body = v
            bodyType = valueTypeInfo
            contentType(contentType)
        }.throwOnUnsuccess { "Unable to clear data with value $v" }
    }

    @OptIn(InternalAPI::class)
    override suspend fun set(toSet: Map<Key, List<Value>>) {
        httpClient.post(
            buildStandardUrl(baseUrl, setRoute)
        ) {
            body = toSet
            bodyType = keyToValuesMapTypeInfo
            contentType(contentType)
        }.throwOnUnsuccess { "Unable to set data $toSet" }
    }

    companion object {
        inline operator fun <reified Key : Any, reified Value : Any> invoke(
            baseUrl: String,
            httpClient: HttpClient,
            contentType: ContentType,
            onNewValue: Flow<Pair<Key, Value>> = httpClient.createStandardWebsocketFlow(
                buildStandardUrl(baseUrl, onNewValueRoute),
            ),
            onValueRemoved: Flow<Pair<Key, Value>> = httpClient.createStandardWebsocketFlow(
                buildStandardUrl(baseUrl, onValueRemovedRoute),
            ),
            onDataCleared: Flow<Key> = httpClient.createStandardWebsocketFlow(
                buildStandardUrl(baseUrl, onDataClearedRoute),
            ),
        ) = KtorWriteKeyValuesRepoClient<Key, Value>(
            baseUrl,
            httpClient,
            contentType,
            onNewValue,
            onValueRemoved,
            onDataCleared,
            typeInfo<Key>(),
            typeInfo<Value>(),
            typeInfo<Map<Key, List<Value>>>()
        )
    }
}
