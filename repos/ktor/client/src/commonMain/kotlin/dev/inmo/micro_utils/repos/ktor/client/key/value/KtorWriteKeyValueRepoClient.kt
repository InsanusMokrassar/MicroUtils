package dev.inmo.micro_utils.repos.ktor.client.key.value

import dev.inmo.micro_utils.ktor.client.createStandardWebsocketFlow
import dev.inmo.micro_utils.ktor.client.throwOnUnsuccess
import dev.inmo.micro_utils.ktor.common.*
import dev.inmo.micro_utils.repos.WriteKeyValueRepo
import dev.inmo.micro_utils.repos.ktor.common.key_value.*
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.http.*
import io.ktor.utils.io.InternalAPI
import io.ktor.util.reflect.TypeInfo
import io.ktor.util.reflect.typeInfo
import kotlinx.coroutines.flow.Flow

class KtorWriteKeyValueRepoClient<Key, Value>(
    private val baseUrl: String,
    private val httpClient: HttpClient,
    private val contentType: ContentType,
    override val onNewValue: Flow<Pair<Key, Value>>,
    override val onValueRemoved: Flow<Key>,
    private val idsListTypeInfo: TypeInfo,
    private val objectsListTypeInfo: TypeInfo,
    private val idsToObjectsMapTypeInfo: TypeInfo
) : WriteKeyValueRepo<Key, Value> {
    @OptIn(InternalAPI::class)
    override suspend fun unsetWithValues(toUnset: List<Value>) {
        httpClient.post(
            buildStandardUrl(baseUrl, unsetWithValuesRoute)
        ) {
            body = toUnset
            bodyType = objectsListTypeInfo
            contentType(contentType)
        }.throwOnUnsuccess { "Unable to unset data with values $toUnset" }
    }

    @OptIn(InternalAPI::class)
    override suspend fun unset(toUnset: List<Key>) {
        httpClient.post(
            buildStandardUrl(baseUrl, unsetRoute)
        ) {
            body = toUnset
            bodyType = idsListTypeInfo
            contentType(contentType)
        }.throwOnUnsuccess { "Unable to unset $toUnset" }
    }

    @OptIn(InternalAPI::class)
    override suspend fun set(toSet: Map<Key, Value>) {
        httpClient.post(
            buildStandardUrl(baseUrl, setRoute)
        ) {
            body = toSet
            bodyType = idsToObjectsMapTypeInfo
            contentType(contentType)
        }.throwOnUnsuccess { "Unable to set $toSet" }
    }

    companion object {
        inline operator fun <reified Key, reified Value> invoke(
            baseUrl: String,
            httpClient: HttpClient,
            contentType: ContentType,
            onNewValue: Flow<Pair<Key, Value>> = httpClient.createStandardWebsocketFlow(
                buildStandardUrl(baseUrl, onNewValueRoute),
            ),
            onValueRemoved: Flow<Key> = httpClient.createStandardWebsocketFlow(
                buildStandardUrl(baseUrl, onValueRemovedRoute),
            ),
        ) = KtorWriteKeyValueRepoClient<Key, Value>(
            baseUrl,
            httpClient,
            contentType,
            onNewValue,
            onValueRemoved,
            typeInfo<List<Key>>(),
            typeInfo<List<Value>>(),
            typeInfo<Map<Key, Value>>()
        )
    }
}
