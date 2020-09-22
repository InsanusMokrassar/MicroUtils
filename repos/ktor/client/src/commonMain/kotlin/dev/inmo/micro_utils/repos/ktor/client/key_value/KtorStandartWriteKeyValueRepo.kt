package dev.inmo.micro_utils.repos.ktor.client.key_value

import dev.inmo.micro_utils.repos.StandardWriteKeyValueRepo
import dev.inmo.micro_utils.repos.ktor.common.key_value.*
import io.ktor.client.*
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.PairSerializer
import kotlinx.serialization.builtins.serializer

class KtorStandartWriteKeyValueRepo<K, V> (
    private var baseUrl: String,
    private var client: HttpClient = HttpClient(),
    private var keySerializer: KSerializer<K>,
    private var valueSerializer: KSerializer<V>,
) : StandardWriteKeyValueRepo<K, V> {
    override val onNewValue: Flow<Pair<K, V>> = client.createStandardWebsocketFlow(
        buildStandardUrl(baseUrl, onNewValueRoute),
        deserializer = PairSerializer(keySerializer, valueSerializer)
    )

    override val onValueRemoved: Flow<K> = client.createStandardWebsocketFlow(
        buildStandardUrl(baseUrl, onValueRemovedRoute),
        deserializer = keySerializer
    )

    override suspend fun set(k: K, v: V) = client.unipost(
        buildStandardUrl(
            baseUrl,
            setRoute
        ),
        BodyPair(KeyValuePostObject.serializer(keySerializer, valueSerializer), KeyValuePostObject(k, v)),
        Unit.serializer()
    )

    override suspend fun unset(k: K) = client.unipost(
        buildStandardUrl(
            baseUrl,
            unsetRoute,
        ),
        BodyPair(keySerializer, k),
        Unit.serializer()
    )
}