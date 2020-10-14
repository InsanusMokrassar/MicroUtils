package dev.inmo.micro_utils.repos.ktor.client.one_to_many

import dev.inmo.micro_utils.repos.OneToManyKeyValueRepo
import dev.inmo.micro_utils.repos.ReadOneToManyKeyValueRepo
import dev.inmo.micro_utils.repos.WriteOneToManyKeyValueRepo
import io.ktor.client.HttpClient
import kotlinx.serialization.KSerializer

class KtorOneToManyKeyValueRepo<Key, Value>(
    baseUrl: String,
    baseSubpart: String,
    client: HttpClient,
    keySerializer: KSerializer<Key>,
    valueSerializer: KSerializer<Value>,
) : OneToManyKeyValueRepo<Key, Value>,
    ReadOneToManyKeyValueRepo<Key, Value> by KtorReadOneToManyKeyValueRepo<Key, Value> (
        "$baseUrl/$baseSubpart",
        client,
        keySerializer,
        valueSerializer,
    ),
    WriteOneToManyKeyValueRepo<Key, Value> by KtorWriteOneToManyKeyValueRepo<Key, Value> (
        "$baseUrl/$baseSubpart",
        client,
        keySerializer,
        valueSerializer,
    )