package dev.inmo.micro_utils.repos.ktor.client.one_to_many

import dev.inmo.micro_utils.ktor.common.*
import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.repos.ReadOneToManyKeyValueRepo
import dev.inmo.micro_utils.repos.ktor.common.*
import dev.inmo.micro_utils.repos.ktor.common.crud.*
import dev.inmo.micro_utils.repos.ktor.common.one_to_many.*
import dev.inmo.micro_utils.repos.ktor.common.reversedParameterName
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.*
import io.ktor.util.reflect.TypeInfo
import io.ktor.util.reflect.typeInfo
import kotlinx.serialization.*

class KtorReadStandardKeyValuesRepoClient<Key, Value>(
    private val baseUrl: String,
    private val httpClient: HttpClient,
    private val contentType: ContentType,
    private val paginationResultValuesTypeInfo: TypeInfo,
    private val paginationResultKeysTypeInfo: TypeInfo,
    private val keySerializer: suspend (Key) -> String,
    private val valueSerializer: suspend (Value) -> String
) : ReadOneToManyKeyValueRepo<Key, Value> {
    override suspend fun get(
        k: Key,
        pagination: Pagination,
        reversed: Boolean
    ): PaginationResult<Value> = httpClient.get(
        buildStandardUrl(
            baseUrl,
            getRoute,
            pagination.asUrlQueryParts + (reversedParameterName to reversed.toString()) + (keyParameterName to keySerializer(k))
        )
    ) {
        contentType(contentType)
    }.body(paginationResultValuesTypeInfo)

    override suspend fun keys(
        pagination: Pagination,
        reversed: Boolean
    ): PaginationResult<Key> = httpClient.get(
        buildStandardUrl(
            baseUrl,
            keysRoute,
            pagination.asUrlQueryParts + (reversedParameterName to reversed.toString())
        )
    ) {
        contentType(contentType)
    }.body(paginationResultKeysTypeInfo)

    override suspend fun keys(
        v: Value,
        pagination: Pagination,
        reversed: Boolean
    ): PaginationResult<Key> = httpClient.get(
        buildStandardUrl(
            baseUrl,
            keysRoute,
            pagination.asUrlQueryParts + (reversedParameterName to reversed.toString()) + (valueParameterName to valueSerializer(v))
        )
    ) {
        contentType(contentType)
    }.body(paginationResultKeysTypeInfo)

    override suspend fun contains(k: Key): Boolean = httpClient.get(
        buildStandardUrl(
            baseUrl,
            containsRoute,
            keyParameterName to keySerializer(k)
        )
    ) {
        contentType(contentType)
    }.body()

    override suspend fun contains(k: Key, v: Value): Boolean = httpClient.get(
        buildStandardUrl(
            baseUrl,
            containsRoute,
            keyParameterName to keySerializer(k),
            valueParameterName to valueSerializer(v)
        )
    ) {
        contentType(contentType)
    }.body()

    override suspend fun count(): Long = httpClient.get(
        buildStandardUrl(
            baseUrl,
            countRouting
        )
    ) {
        contentType(contentType)
    }.body()

    override suspend fun count(k: Key): Long = httpClient.get(
        buildStandardUrl(
            baseUrl,
            countRouting,
            keyParameterName to keySerializer(k),
        )
    ) {
        contentType(contentType)
    }.body()
}

inline fun <reified Key, reified Value> KtorReadStandardKeyValuesRepoClient(
    baseUrl: String,
    httpClient: HttpClient,
    contentType: ContentType,
    noinline keySerializer: suspend (Key) -> String,
    noinline valueSerializer: suspend (Value) -> String
) = KtorReadStandardKeyValuesRepoClient<Key, Value>(
    baseUrl,
    httpClient,
    contentType,
    typeInfo<PaginationResult<Value>>(),
    typeInfo<PaginationResult<Key>>(),
    keySerializer,
    valueSerializer
)

inline fun <reified Key, reified Value> KtorReadStandardKeyValuesRepoClient(
    baseUrl: String,
    httpClient: HttpClient,
    idsSerializer: KSerializer<Key>,
    valueSerializer: KSerializer<Value>,
    serialFormat: StringFormat,
    contentType: ContentType,
) = KtorReadStandardKeyValuesRepoClient<Key, Value>(
    baseUrl,
    httpClient,
    contentType,
    {
        serialFormat.encodeToString(idsSerializer, it).encodeURLQueryComponent()
    }
) {
    serialFormat.encodeToString(valueSerializer, it).encodeURLQueryComponent()
}

inline fun <reified Key, reified Value> KtorReadStandardKeyValuesRepoClient(
    baseUrl: String,
    httpClient: HttpClient,
    idsSerializer: KSerializer<Key>,
    valuesSerializer: KSerializer<Value>,
    serialFormat: BinaryFormat,
    contentType: ContentType,
) = KtorReadStandardKeyValuesRepoClient<Key, Value>(
    baseUrl,
    httpClient,
    contentType,
    {
        serialFormat.encodeHex(idsSerializer, it)
    }
) {
    serialFormat.encodeHex(valuesSerializer, it)
}
