package dev.inmo.micro_utils.repos.ktor.client.key.value

import dev.inmo.micro_utils.ktor.common.*
import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.repos.ReadKeyValueRepo
import dev.inmo.micro_utils.repos.ktor.common.*
import dev.inmo.micro_utils.repos.ktor.common.containsRoute
import dev.inmo.micro_utils.repos.ktor.common.keyParameterName
import dev.inmo.micro_utils.repos.ktor.common.key_value.*
import dev.inmo.micro_utils.repos.ktor.common.reversedParameterName
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.*
import io.ktor.util.reflect.TypeInfo
import io.ktor.util.reflect.typeInfo
import kotlinx.serialization.*

class KtorReadKeyValueRepoClient<Key, Value>(
    private val baseUrl: String,
    private val httpClient: HttpClient,
    private val contentType: ContentType,
    private val objectType: TypeInfo,
    private val paginationResultObjectsTypeInfo: TypeInfo,
    private val paginationResultIdsTypeInfo: TypeInfo,
    private val idSerializer: suspend (Key) -> String,
    private val valueSerializer: suspend (Value) -> String
) : ReadKeyValueRepo<Key, Value> {
    override suspend fun get(k: Key): Value? = httpClient.get(
        buildStandardUrl(
            baseUrl,
            getRoute,
            mapOf(
                keyParameterName to idSerializer(k)
            )
        )
    ) {
        contentType(contentType)
    }.takeIf { it.status != HttpStatusCode.NoContent } ?.body<Value>(objectType)

    override suspend fun contains(key: Key): Boolean = httpClient.get(
        buildStandardUrl(
            baseUrl,
            containsRoute,
            keyParameterName to idSerializer(key)
        )
    ) {
        contentType(contentType)
    }.body()

    override suspend fun getAll(): Map<Key, Value> = httpClient.get(
        buildStandardUrl(
            baseUrl,
            getAllRoute
        )
    ) {
        contentType(contentType)
    }.body()

    override suspend fun values(
        pagination: Pagination,
        reversed: Boolean
    ): PaginationResult<Value> = httpClient.get(
        buildStandardUrl(baseUrl, valuesRoute, pagination.asUrlQueryParts + (reversedParameterName to reversed.toString()))
    ) {
        contentType(contentType)
    }.body(paginationResultObjectsTypeInfo)

    override suspend fun keys(
        pagination: Pagination,
        reversed: Boolean
    ): PaginationResult<Key> = httpClient.get(
        buildStandardUrl(baseUrl, keysRoute, pagination.asUrlQueryParts + (reversedParameterName to reversed.toString()))
    ) {
        contentType(contentType)
    }.body(paginationResultIdsTypeInfo)

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
    }.body(paginationResultIdsTypeInfo)

    override suspend fun count(): Long = httpClient.get(
        buildStandardUrl(
            baseUrl,
            dev.inmo.micro_utils.repos.ktor.common.countRoute
        )
    ) {
        contentType(contentType)
    }.body()
}

inline fun <reified Key, reified Value> KtorReadKeyValueRepoClient(
    baseUrl: String,
    httpClient: HttpClient,
    contentType: ContentType,
    noinline idSerializer: suspend (Key) -> String,
    noinline valueSerializer: suspend (Value) -> String
) = KtorReadKeyValueRepoClient<Key, Value>(
    baseUrl,
    httpClient,
    contentType,
    typeInfo<Value>(),
    typeInfo<PaginationResult<Value>>(),
    typeInfo<PaginationResult<Key>>(),
    idSerializer,
    valueSerializer
)

inline fun <reified Key, reified Value> KtorReadKeyValueRepoClient(
    baseUrl: String,
    httpClient: HttpClient,
    idsSerializer: KSerializer<Key>,
    valueSerializer: KSerializer<Value>,
    serialFormat: StringFormat,
    contentType: ContentType,
) = KtorReadKeyValueRepoClient<Key, Value>(
    baseUrl,
    httpClient,
    contentType,
    {
        serialFormat.encodeToString(idsSerializer, it).encodeURLQueryComponent()
    }
) {
    serialFormat.encodeToString(valueSerializer, it).encodeURLQueryComponent()
}

inline fun <reified Key, reified Value> KtorReadKeyValueRepoClient(
    baseUrl: String,
    httpClient: HttpClient,
    idsSerializer: KSerializer<Key>,
    valuesSerializer: KSerializer<Value>,
    serialFormat: BinaryFormat,
    contentType: ContentType,
) = KtorReadKeyValueRepoClient<Key, Value>(
    baseUrl,
    httpClient,
    contentType,
    {
        serialFormat.encodeHex(idsSerializer, it)
    }
) {
    serialFormat.encodeHex(valuesSerializer, it)
}
