package dev.inmo.micro_utils.repos.ktor.client.key_value

import dev.inmo.micro_utils.repos.StandardKeyValueRepo
import dev.inmo.micro_utils.repos.StandardReadKeyValueRepo
import dev.inmo.micro_utils.repos.StandardWriteKeyValueRepo
import io.ktor.client.*
import kotlinx.serialization.KSerializer

class KtorStandartKeyValueRepo<K, V> (
    baseUrl: String,
    baseSubpart: String,
    client: HttpClient = HttpClient(),
    keySerializer: KSerializer<K>,
    valueSerializer: KSerializer<V>,
    valueNullableSerializer: KSerializer<V?>
) : StandardKeyValueRepo<K, V>,
    StandardReadKeyValueRepo<K, V> by KtorStandartReadKeyValueRepo(
        "$baseUrl/$baseSubpart",
        client,
        keySerializer,
        valueSerializer,
        valueNullableSerializer
    ),
    StandardWriteKeyValueRepo<K, V> by KtorStandartWriteKeyValueRepo(
        "$baseUrl/$baseSubpart",
        client,
        keySerializer,
        valueSerializer
    )