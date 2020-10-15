package dev.inmo.micro_utils.repos.ktor.client.key_value

import dev.inmo.micro_utils.ktor.client.BodyPair
import dev.inmo.micro_utils.ktor.client.createStandardWebsocketFlow
import dev.inmo.micro_utils.ktor.client.unipost
import dev.inmo.micro_utils.ktor.common.buildStandardUrl
import dev.inmo.micro_utils.repos.WriteStandardKeyValueRepo
import dev.inmo.micro_utils.repos.ktor.common.key_value.*
import io.ktor.client.*
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.PairSerializer
import kotlinx.serialization.builtins.serializer
import kotlin.js.JsExport

class KtorWriteStandardKeyValueRepo<K, V> (
    private var baseUrl: String,
    private var client: HttpClient = HttpClient(),
    private var keySerializer: KSerializer<K>,
    private var valueSerializer: KSerializer<V>,
) : WriteStandardKeyValueRepo<K, V> {
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

@Deprecated("Renamed", ReplaceWith("KtorWriteStandardKeyValueRepo", "dev.inmo.micro_utils.repos.ktor.client.key_value.KtorWriteStandardKeyValueRepo"))
typealias KtorStandartWriteKeyValueRepo<K, V> = KtorWriteStandardKeyValueRepo<K, V>
