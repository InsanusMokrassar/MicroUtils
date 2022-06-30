package dev.inmo.micro_utils.repos.ktor.client.key_value

import dev.inmo.micro_utils.ktor.client.*
import dev.inmo.micro_utils.ktor.common.*
import dev.inmo.micro_utils.repos.WriteKeyValueRepo
import dev.inmo.micro_utils.repos.ktor.common.key_value.*
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.*

@Deprecated("Replaced with KtorWriteKeyValueRepoClient")
class KtorWriteStandardKeyValueRepo<K, V> (
    private var baseUrl: String,
    private var unifiedRequester: UnifiedRequester,
    private var keySerializer: KSerializer<K>,
    private var valueSerializer: KSerializer<V>,
) : WriteKeyValueRepo<K, V> {
    private val keyValueMapSerializer = MapSerializer(keySerializer, valueSerializer)
    private val keysListSerializer = ListSerializer(keySerializer)
    private val valuesListSerializer = ListSerializer(valueSerializer)

    constructor(
        baseUrl: String,
        client: HttpClient,
        keySerializer: KSerializer<K>,
        valueSerializer: KSerializer<V>,
        serialFormat: StandardKtorSerialFormat = standardKtorSerialFormat
    ) : this (
        baseUrl, UnifiedRequester(client, serialFormat), keySerializer, valueSerializer
    )

    override val onNewValue: Flow<Pair<K, V>> = unifiedRequester.createStandardWebsocketFlow(
        buildStandardUrl(baseUrl, onNewValueRoute),
        deserializer = PairSerializer(keySerializer, valueSerializer)
    )

    override val onValueRemoved: Flow<K> = unifiedRequester.createStandardWebsocketFlow(
        buildStandardUrl(baseUrl, onValueRemovedRoute),
        deserializer = keySerializer
    )

    override suspend fun set(toSet: Map<K, V>) = unifiedRequester.unipost(
        buildStandardUrl(
            baseUrl,
            setRoute
        ),
        Pair(keyValueMapSerializer, toSet),
        Unit.serializer()
    )

    override suspend fun unset(toUnset: List<K>) = unifiedRequester.unipost(
        buildStandardUrl(
            baseUrl,
            unsetRoute,
        ),
        Pair(keysListSerializer, toUnset),
        Unit.serializer()
    )

    override suspend fun unsetWithValues(toUnset: List<V>) = unifiedRequester.unipost(
        buildStandardUrl(
            baseUrl,
            unsetWithValuesRoute,
        ),
        Pair(valuesListSerializer, toUnset),
        Unit.serializer()
    )
}
