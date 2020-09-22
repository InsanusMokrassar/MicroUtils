package dev.inmo.micro_utils.repos.ktor.client.one_to_many

import dev.inmo.micro_utils.repos.OneToManyKeyValueRepo
import dev.inmo.micro_utils.repos.OneToManyReadKeyValueRepo
import dev.inmo.micro_utils.repos.OneToManyWriteKeyValueRepo
import io.ktor.client.HttpClient
import kotlinx.serialization.KSerializer

class KtorOneToManyKeyValueRepo<Key, Value>(
    baseUrl: String,
    baseSubpart: String,
    client: HttpClient,
    keySerializer: KSerializer<Key>,
    valueSerializer: KSerializer<Value>,
) : OneToManyKeyValueRepo<Key, Value>,
    OneToManyReadKeyValueRepo<Key, Value> by KtorOneToManyReadKeyValueRepo<Key, Value> (
        "$baseUrl/$baseSubpart",
        client,
        keySerializer,
        valueSerializer,
    ),
    OneToManyWriteKeyValueRepo<Key, Value> by KtorOneToManyWriteKeyValueRepo<Key, Value> (
        "$baseUrl/$baseSubpart",
        client,
        keySerializer,
        valueSerializer,
    )