package dev.inmo.micro_utils.repos.ktor.client.key_value

import dev.inmo.micro_utils.ktor.client.UnifiedRequester
import dev.inmo.micro_utils.ktor.common.StandardKtorSerialFormat
import dev.inmo.micro_utils.ktor.common.standardKtorSerialFormat
import dev.inmo.micro_utils.repos.*
import io.ktor.client.HttpClient
import kotlinx.serialization.*

class KtorStandartKeyValueRepo<K, V> (
    baseUrl: String,
    baseSubpart: String,
    unifiedRequester: UnifiedRequester,
    keySerializer: KSerializer<K>,
    valueSerializer: KSerializer<V>,
    valueNullableSerializer: KSerializer<V?>
) : StandardKeyValueRepo<K, V>,
    ReadStandardKeyValueRepo<K, V> by KtorReadStandardKeyValueRepo(
        "$baseUrl/$baseSubpart",
        unifiedRequester,
        keySerializer,
        valueSerializer,
        valueNullableSerializer
    ),
    WriteStandardKeyValueRepo<K, V> by KtorWriteStandardKeyValueRepo(
        "$baseUrl/$baseSubpart",
        unifiedRequester,
        keySerializer,
        valueSerializer
    ) {
    constructor(
        baseUrl: String,
        baseSubpart: String,
        client: HttpClient = HttpClient(),
        keySerializer: KSerializer<K>,
        valueSerializer: KSerializer<V>,
        valueNullableSerializer: KSerializer<V?>,
        serialFormat: StandardKtorSerialFormat = standardKtorSerialFormat
    ) : this(baseUrl, baseSubpart, UnifiedRequester(client, serialFormat), keySerializer, valueSerializer, valueNullableSerializer)
}