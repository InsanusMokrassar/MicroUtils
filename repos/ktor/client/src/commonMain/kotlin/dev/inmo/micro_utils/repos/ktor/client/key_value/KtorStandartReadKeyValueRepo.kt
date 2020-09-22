package dev.inmo.micro_utils.repos.ktor.client.key_value

import dev.inmo.micro_utils.pagination.Pagination
import dev.inmo.micro_utils.pagination.PaginationResult
import dev.inmo.micro_utils.repos.StandardReadKeyValueRepo
import dev.inmo.micro_utils.repos.ktor.common.key_value.*
import io.ktor.client.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer

class KtorStandartReadKeyValueRepo<Key, Value> (
    private var baseUrl: String,
    private var client: HttpClient = HttpClient(),
    private var keySerializer: KSerializer<Key>,
    private var valueSerializer: KSerializer<Value>,
    private var valueNullableSerializer: KSerializer<Value?>,
) : StandardReadKeyValueRepo<Key, Value> {
    override suspend fun get(k: Key): Value? = client.uniget(
        buildStandardUrl(
            baseUrl,
            getRoute,
            mapOf(
                keyParameterName to keySerializer.encodeUrlQueryValue(k)
            )
        ),
        valueNullableSerializer
    )

    override suspend fun values(pagination: Pagination, reversed: Boolean): PaginationResult<Value> = client.uniget(
        buildStandardUrl(
            baseUrl,
            valuesRoute,
            mapOf(
                reversedParameterName to Boolean.serializer().encodeUrlQueryValue(reversed)
            ) + pagination.asUrlQueryParts
        ),
        PaginationResult.serializer(valueSerializer)
    )

    override suspend fun keys(pagination: Pagination, reversed: Boolean): PaginationResult<Key> = client.uniget(
        buildStandardUrl(
            baseUrl,
            keysRoute,
            mapOf(
                reversedParameterName to Boolean.serializer().encodeUrlQueryValue(reversed)
            ) + pagination.asUrlQueryParts
        ),
        PaginationResult.serializer(keySerializer)
    )

    override suspend fun contains(key: Key): Boolean = client.uniget(
        buildStandardUrl(
            baseUrl,
            containsRoute,
            mapOf(
                keyParameterName to keySerializer.encodeUrlQueryValue(key)
            ),
        ),
        Boolean.serializer(),
    )

    override suspend fun count(): Long = client.uniget(
        buildStandardUrl(
            baseUrl,
            countRoute,
        ),
        Long.serializer()
    )
}