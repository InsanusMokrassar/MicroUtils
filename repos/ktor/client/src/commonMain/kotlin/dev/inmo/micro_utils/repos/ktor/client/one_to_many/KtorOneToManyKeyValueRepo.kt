package dev.inmo.micro_utils.repos.ktor.client.one_to_many

import dev.inmo.micro_utils.ktor.client.UnifiedRequester
import dev.inmo.micro_utils.ktor.common.StandardKtorSerialFormat
import dev.inmo.micro_utils.ktor.common.standardKtorSerialFormat
import dev.inmo.micro_utils.repos.*
import io.ktor.client.HttpClient
import kotlinx.serialization.KSerializer

@Deprecated("Should be replaced with KtorKeyValuesRepoClient")
class KtorOneToManyKeyValueRepo<Key, Value>(
    baseUrl: String,
    baseSubpart: String,
    unifiedRequester: UnifiedRequester,
    keySerializer: KSerializer<Key>,
    valueSerializer: KSerializer<Value>,
) : KeyValuesRepo<Key, Value>,
    ReadKeyValuesRepo<Key, Value> by KtorReadOneToManyKeyValueRepo<Key, Value> (
        "$baseUrl/$baseSubpart",
        unifiedRequester,
        keySerializer,
        valueSerializer,
    ),
    WriteKeyValuesRepo<Key, Value> by KtorWriteOneToManyKeyValueRepo<Key, Value> (
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
