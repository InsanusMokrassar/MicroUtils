package dev.inmo.micro_utils.repos.ktor.client.one_to_many

import dev.inmo.micro_utils.ktor.client.UnifiedRequester
import dev.inmo.micro_utils.ktor.common.StandardKtorSerialFormat
import dev.inmo.micro_utils.ktor.common.standardKtorSerialFormat
import dev.inmo.micro_utils.repos.*
import io.ktor.client.HttpClient
import kotlinx.serialization.KSerializer

class KtorOneToManyKeyValueRepo<Key, Value>(
    baseUrl: String,
    baseSubpart: String,
    unifiedRequester: UnifiedRequester,
    keySerializer: KSerializer<Key>,
    valueSerializer: KSerializer<Value>,
) : OneToManyKeyValueRepo<Key, Value>,
    ReadOneToManyKeyValueRepo<Key, Value> by KtorReadOneToManyKeyValueRepo<Key, Value> (
        "$baseUrl/$baseSubpart",
        unifiedRequester,
        keySerializer,
        valueSerializer,
    ),
    WriteOneToManyKeyValueRepo<Key, Value> by KtorWriteOneToManyKeyValueRepo<Key, Value> (
        "$baseUrl/$baseSubpart",
        unifiedRequester,
        keySerializer,
        valueSerializer,
    ) {
    constructor(
        baseUrl: String,
        baseSubpart: String,
        client: HttpClient,
        keySerializer: KSerializer<Key>,
        valueSerializer: KSerializer<Value>,
        serialFormat: StandardKtorSerialFormat = standardKtorSerialFormat
    ) : this (baseUrl, baseSubpart, UnifiedRequester(client, serialFormat), keySerializer, valueSerializer)
}