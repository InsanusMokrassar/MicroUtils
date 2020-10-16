package dev.inmo.micro_utils.repos.ktor.client.key_value

import dev.inmo.micro_utils.repos.StandardKeyValueRepo
import dev.inmo.micro_utils.repos.ReadStandardKeyValueRepo
import dev.inmo.micro_utils.repos.WriteStandardKeyValueRepo
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
    ReadStandardKeyValueRepo<K, V> by KtorReadStandardKeyValueRepo(
        "$baseUrl/$baseSubpart",
        client,
        keySerializer,
        valueSerializer,
        valueNullableSerializer
    ),
    WriteStandardKeyValueRepo<K, V> by KtorWriteStandardKeyValueRepo(
        "$baseUrl/$baseSubpart",
        client,
        keySerializer,
        valueSerializer
    )