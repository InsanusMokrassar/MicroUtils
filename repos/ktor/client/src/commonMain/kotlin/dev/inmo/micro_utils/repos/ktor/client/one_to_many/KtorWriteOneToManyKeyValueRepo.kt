package dev.inmo.micro_utils.repos.ktor.client.one_to_many

import dev.inmo.micro_utils.ktor.client.*
import dev.inmo.micro_utils.ktor.common.*
import dev.inmo.micro_utils.repos.WriteOneToManyKeyValueRepo
import dev.inmo.micro_utils.repos.ktor.common.one_to_many.*
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.*

class KtorWriteOneToManyKeyValueRepo<Key, Value> (
    private val baseUrl: String,
    private val unifiedRequester: UnifiedRequester,
    private val keySerializer: KSerializer<Key>,
    private val valueSerializer: KSerializer<Value>
) : WriteOneToManyKeyValueRepo<Key, Value> {
    private val keyValueSerializer = PairSerializer(keySerializer, valueSerializer)
    private val keyValueMapSerializer = MapSerializer(keySerializer, ListSerializer(valueSerializer))

    constructor(
        baseUrl: String,
        client: HttpClient,
        keySerializer: KSerializer<Key>,
        valueSerializer: KSerializer<Value>,
        serialFormat: StandardKtorSerialFormat = standardKtorSerialFormat
    ) : this (
        baseUrl, UnifiedRequester(client, serialFormat), keySerializer, valueSerializer
    )

    override val onNewValue: Flow<Pair<Key, Value>> = unifiedRequester.createStandardWebsocketFlow(
        buildStandardUrl(baseUrl, onNewValueRoute),
        deserializer = keyValueSerializer
    )
    override val onValueRemoved: Flow<Pair<Key, Value>> = unifiedRequester.createStandardWebsocketFlow(
        buildStandardUrl(baseUrl, onValueRemovedRoute),
        deserializer = keyValueSerializer
    )
    override val onDataCleared: Flow<Key> = unifiedRequester.createStandardWebsocketFlow(
        buildStandardUrl(baseUrl, onDataClearedRoute),
        deserializer = keySerializer
    )

    override suspend fun remove(toRemove: Map<Key, List<Value>>) = unifiedRequester.unipost(
        buildStandardUrl(
            baseUrl,
            removeRoute,
        ),
        BodyPair(keyValueMapSerializer, toRemove),
        Unit.serializer(),
    )

    override suspend fun add(toAdd: Map<Key, List<Value>>) = unifiedRequester.unipost(
        buildStandardUrl(
            baseUrl,
            addRoute,
        ),
        BodyPair(keyValueMapSerializer, toAdd),
        Unit.serializer(),
    )
    override suspend fun clear(k: Key) = unifiedRequester.unipost(
        buildStandardUrl(
            baseUrl,
            clearRoute,
        ),
        BodyPair(keySerializer, k),
        Unit.serializer(),
    )

    override suspend fun set(toSet: Map<Key, List<Value>>) = unifiedRequester.unipost(
        buildStandardUrl(
            baseUrl,
            setRoute,
        ),
        BodyPair(keyValueMapSerializer, toSet),
        Unit.serializer(),
    )
}