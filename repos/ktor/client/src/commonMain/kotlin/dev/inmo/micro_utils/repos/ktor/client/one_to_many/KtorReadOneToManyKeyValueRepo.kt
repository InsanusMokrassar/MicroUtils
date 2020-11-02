package dev.inmo.micro_utils.repos.ktor.client.one_to_many

import dev.inmo.micro_utils.ktor.client.encodeUrlQueryValue
import dev.inmo.micro_utils.ktor.client.uniget
import dev.inmo.micro_utils.ktor.common.buildStandardUrl
import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.repos.ReadOneToManyKeyValueRepo
import dev.inmo.micro_utils.repos.ktor.common.one_to_many.*
import io.ktor.client.HttpClient
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer

class KtorReadOneToManyKeyValueRepo<Key, Value> (
    private val baseUrl: String,
    private val client: HttpClient = HttpClient(),
    private val keySerializer: KSerializer<Key>,
    private val valueSerializer: KSerializer<Value>,
) : ReadOneToManyKeyValueRepo<Key, Value> {
    private val paginationValueResultSerializer = PaginationResult.serializer(valueSerializer)
    private val paginationKeyResultSerializer = PaginationResult.serializer(keySerializer)

    override suspend fun get(k: Key, pagination: Pagination, reversed: Boolean): PaginationResult<Value> = client.uniget(
        buildStandardUrl(
            baseUrl,
            getRoute,
            mapOf(
                keyParameterName to keySerializer.encodeUrlQueryValue(k),
                reversedParameterName to Boolean.serializer().encodeUrlQueryValue(reversed)
            ) + pagination.asUrlQueryParts
        ),
        paginationValueResultSerializer
    )

    override suspend fun keys(pagination: Pagination, reversed: Boolean): PaginationResult<Key> = client.uniget(
        buildStandardUrl(
            baseUrl,
            keysRoute,
            mapOf(
                reversedParameterName to Boolean.serializer().encodeUrlQueryValue(reversed)
            ) + pagination.asUrlQueryParts
        ),
        paginationKeyResultSerializer
    )

    override suspend fun contains(k: Key): Boolean = client.uniget(
        buildStandardUrl(
            baseUrl,
            containsByKeyRoute,
            mapOf(keyParameterName to keySerializer.encodeUrlQueryValue(k))
        ),
        Boolean.serializer()
    )

    override suspend fun contains(k: Key, v: Value): Boolean = client.uniget(
        buildStandardUrl(
            baseUrl,
            containsByKeyValueRoute,
            mapOf(
                keyParameterName to keySerializer.encodeUrlQueryValue(k),
                valueParameterName to valueSerializer.encodeUrlQueryValue(v),
            )
        ),
        Boolean.serializer()
    )

    override suspend fun count(k: Key): Long = client.uniget(
        buildStandardUrl(
            baseUrl,
            countByKeyRoute,
            mapOf(
                keyParameterName to keySerializer.encodeUrlQueryValue(k)
            )
        ),
        Long.serializer()
    )

    override suspend fun count(): Long = client.uniget(
        buildStandardUrl(
            baseUrl,
            countRoute,
        ),
        Long.serializer()
    )

}