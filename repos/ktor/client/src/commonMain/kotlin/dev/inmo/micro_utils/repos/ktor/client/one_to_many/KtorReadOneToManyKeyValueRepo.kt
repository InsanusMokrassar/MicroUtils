package dev.inmo.micro_utils.repos.ktor.client.one_to_many

import dev.inmo.micro_utils.ktor.client.*
import dev.inmo.micro_utils.ktor.common.buildStandardUrl
import dev.inmo.micro_utils.ktor.common.standardKtorSerialFormat
import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.repos.ReadOneToManyKeyValueRepo
import dev.inmo.micro_utils.repos.ktor.common.keyParameterName
import dev.inmo.micro_utils.repos.ktor.common.one_to_many.*
import dev.inmo.micro_utils.repos.ktor.common.reversedParameterName
import dev.inmo.micro_utils.repos.ktor.common.valueParameterName
import io.ktor.client.HttpClient
import kotlinx.serialization.BinaryFormat
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer

class KtorReadOneToManyKeyValueRepo<Key, Value> (
    private val baseUrl: String,
    private val unifiedRequester: UnifiedRequester,
    private val keySerializer: KSerializer<Key>,
    private val valueSerializer: KSerializer<Value>
) : ReadOneToManyKeyValueRepo<Key, Value> {
    private val paginationValueResultSerializer = PaginationResult.serializer(valueSerializer)
    private val paginationKeyResultSerializer = PaginationResult.serializer(keySerializer)

    constructor(
        baseUrl: String,
        client: HttpClient,
        keySerializer: KSerializer<Key>,
        valueSerializer: KSerializer<Value>,
        serialFormat: BinaryFormat = standardKtorSerialFormat
    ) : this (baseUrl, UnifiedRequester(client, serialFormat), keySerializer, valueSerializer)

    override suspend fun get(k: Key, pagination: Pagination, reversed: Boolean): PaginationResult<Value> = unifiedRequester.uniget(
        buildStandardUrl(
            baseUrl,
            getRoute,
            mapOf(
                keyParameterName to unifiedRequester.encodeUrlQueryValue(keySerializer, k),
                reversedParameterName to unifiedRequester.encodeUrlQueryValue(Boolean.serializer(), reversed)
            ) + pagination.asUrlQueryParts
        ),
        paginationValueResultSerializer
    )

    override suspend fun keys(pagination: Pagination, reversed: Boolean): PaginationResult<Key> = unifiedRequester.uniget(
        buildStandardUrl(
            baseUrl,
            keysRoute,
            mapOf(
                reversedParameterName to unifiedRequester.encodeUrlQueryValue(Boolean.serializer(), reversed)
            ) + pagination.asUrlQueryParts
        ),
        paginationKeyResultSerializer
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
        paginationKeyResultSerializer
    )

    override suspend fun contains(k: Key): Boolean = unifiedRequester.uniget(
        buildStandardUrl(
            baseUrl,
            containsByKeyRoute,
            mapOf(keyParameterName to unifiedRequester.encodeUrlQueryValue(keySerializer, k))
        ),
        Boolean.serializer()
    )

    override suspend fun contains(k: Key, v: Value): Boolean = unifiedRequester.uniget(
        buildStandardUrl(
            baseUrl,
            containsByKeyValueRoute,
            mapOf(
                keyParameterName to unifiedRequester.encodeUrlQueryValue(keySerializer, k),
                valueParameterName to unifiedRequester.encodeUrlQueryValue(valueSerializer, v),
            )
        ),
        Boolean.serializer()
    )

    override suspend fun count(k: Key): Long = unifiedRequester.uniget(
        buildStandardUrl(
            baseUrl,
            countByKeyRoute,
            mapOf(
                keyParameterName to unifiedRequester.encodeUrlQueryValue(keySerializer, k)
            )
        ),
        Long.serializer()
    )

    override suspend fun count(): Long = unifiedRequester.uniget(
        buildStandardUrl(
            baseUrl,
            countRoute,
        ),
        Long.serializer()
    )

}