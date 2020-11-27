package dev.inmo.micro_utils.repos.ktor.client.key_value

import dev.inmo.micro_utils.ktor.client.*
import dev.inmo.micro_utils.ktor.common.*
import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.repos.ReadStandardKeyValueRepo
import dev.inmo.micro_utils.repos.ktor.common.key_value.*
import dev.inmo.micro_utils.repos.ktor.common.valueParameterName
import io.ktor.client.HttpClient
import kotlinx.serialization.*
import kotlinx.serialization.builtins.serializer

class KtorReadStandardKeyValueRepo<Key, Value> (
    private val baseUrl: String,
    private val unifiedRequester: UnifiedRequester,
    private val keySerializer: KSerializer<Key>,
    private val valueSerializer: KSerializer<Value>,
    private val valueNullableSerializer: KSerializer<Value?>
) : ReadStandardKeyValueRepo<Key, Value> {
    constructor(
        baseUrl: String,
        client: HttpClient,
        keySerializer: KSerializer<Key>,
        valueSerializer: KSerializer<Value>,
        valueNullableSerializer: KSerializer<Value?>,
        serialFormat: StandardKtorSerialFormat = standardKtorSerialFormat
    ) : this (
        baseUrl, UnifiedRequester(client, serialFormat), keySerializer, valueSerializer, valueNullableSerializer
    )

    override suspend fun get(k: Key): Value? = unifiedRequester.uniget(
        buildStandardUrl(
            baseUrl,
            getRoute,
            mapOf(
                keyParameterName to unifiedRequester.encodeUrlQueryValue(keySerializer, k)
            )
        ),
        valueNullableSerializer
    )

    override suspend fun values(pagination: Pagination, reversed: Boolean): PaginationResult<Value> = unifiedRequester.uniget(
        buildStandardUrl(
            baseUrl,
            valuesRoute,
            mapOf(
                reversedParameterName to unifiedRequester.encodeUrlQueryValue(Boolean.serializer(), reversed)
            ) + pagination.asUrlQueryParts
        ),
        PaginationResult.serializer(valueSerializer)
    )

    override suspend fun keys(pagination: Pagination, reversed: Boolean): PaginationResult<Key> = unifiedRequester.uniget(
        buildStandardUrl(
            baseUrl,
            keysRoute,
            mapOf(
                reversedParameterName to unifiedRequester.encodeUrlQueryValue(Boolean.serializer(), reversed)
            ) + pagination.asUrlQueryParts
        ),
        PaginationResult.serializer(keySerializer)
    )

    override suspend fun keys(v: Value, pagination: Pagination, reversed: Boolean): PaginationResult<Key> = unifiedRequester.uniget(
        buildStandardUrl(
            baseUrl,
            keysRoute,
            mapOf(
                valueParameterName to unifiedRequester.encodeUrlQueryValue(valueSerializer, v),
                reversedParameterName to unifiedRequester.encodeUrlQueryValue(Boolean.serializer(), reversed)
            ) + pagination.asUrlQueryParts
        ),
        PaginationResult.serializer(keySerializer)
    )

    override suspend fun contains(key: Key): Boolean = unifiedRequester.uniget(
        buildStandardUrl(
            baseUrl,
            containsRoute,
            mapOf(
                keyParameterName to unifiedRequester.encodeUrlQueryValue(keySerializer, key)
            ),
        ),
        Boolean.serializer(),
    )

    override suspend fun count(): Long = unifiedRequester.uniget(
        buildStandardUrl(
            baseUrl,
            countRoute,
        ),
        Long.serializer()
    )
}
