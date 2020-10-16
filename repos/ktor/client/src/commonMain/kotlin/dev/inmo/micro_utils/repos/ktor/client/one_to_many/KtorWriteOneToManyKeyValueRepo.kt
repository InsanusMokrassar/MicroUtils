package dev.inmo.micro_utils.repos.ktor.client.one_to_many

import dev.inmo.micro_utils.coroutines.broadcastStateFlow
import dev.inmo.micro_utils.ktor.client.*
import dev.inmo.micro_utils.ktor.common.buildStandardUrl
import dev.inmo.micro_utils.repos.WriteOneToManyKeyValueRepo
import dev.inmo.micro_utils.repos.ktor.common.one_to_many.*
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.PairSerializer
import kotlinx.serialization.builtins.serializer

class KtorWriteOneToManyKeyValueRepo<Key, Value> (
    private val baseUrl: String,
    private val client: HttpClient = HttpClient(),
    private val keySerializer: KSerializer<Key>,
    private val valueSerializer: KSerializer<Value>
) : WriteOneToManyKeyValueRepo<Key, Value> {
    private val keyValueSerializer = PairSerializer(keySerializer, valueSerializer)
    override val onNewValue: Flow<Pair<Key, Value>> = client.createStandardWebsocketFlow(
        buildStandardUrl(baseUrl, onNewValueRoute),
        deserializer = keyValueSerializer
    )
    override val onValueRemoved: Flow<Pair<Key, Value>> = client.createStandardWebsocketFlow(
        buildStandardUrl(baseUrl, onValueRemovedRoute),
        deserializer = keyValueSerializer
    )
    override val onDataCleared: Flow<Key> = client.createStandardWebsocketFlow(
        buildStandardUrl(baseUrl, onDataClearedRoute),
        deserializer = keySerializer
    )

    override suspend fun add(k: Key, v: Value) = client.unipost(
        buildStandardUrl(
            baseUrl,
            addRoute,
        ),
        BodyPair(keyValueSerializer, k to v),
        Unit.serializer(),
    )

    override suspend fun remove(k: Key, v: Value) = client.unipost(
        buildStandardUrl(
            baseUrl,
            removeRoute,
        ),
        BodyPair(keyValueSerializer, k to v),
        Unit.serializer(),
    )

    override suspend fun clear(k: Key)  = client.unipost(
        buildStandardUrl(
            baseUrl,
            clearRoute,
        ),
        BodyPair(keySerializer, k),
        Unit.serializer(),
    )
}