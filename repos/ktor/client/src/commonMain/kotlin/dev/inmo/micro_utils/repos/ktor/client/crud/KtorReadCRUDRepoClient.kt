package dev.inmo.micro_utils.repos.ktor.client.crud

import dev.inmo.micro_utils.ktor.common.*
import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.repos.ReadCRUDRepo
import dev.inmo.micro_utils.repos.ktor.common.countRouting
import dev.inmo.micro_utils.repos.ktor.common.crud.*
import dev.inmo.micro_utils.repos.ktor.common.getAllRoute
import dev.inmo.micro_utils.repos.ktor.common.idParameterName
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.*
import io.ktor.util.reflect.TypeInfo
import io.ktor.util.reflect.typeInfo
import kotlinx.serialization.*

class KtorReadCRUDRepoClient<ObjectType, IdType> (
    private val baseUrl: String,
    private val httpClient: HttpClient,
    private val objectType: TypeInfo,
    private val paginationObjectType: TypeInfo,
    private val paginationIdType: TypeInfo,
    private val contentType: ContentType,
    private val mapTypeInfo: TypeInfo,
    private val idSerializer: suspend (IdType) -> String
) : ReadCRUDRepo<ObjectType, IdType> {
    override suspend fun getByPagination(pagination: Pagination): PaginationResult<ObjectType> = httpClient.get(
        buildStandardUrl(baseUrl, getByPaginationRouting, pagination.asUrlQueryParts)
    ) {
        contentType(contentType)
    }.body(paginationObjectType)
    override suspend fun getIdsByPagination(pagination: Pagination): PaginationResult<IdType> = httpClient.get(
        buildStandardUrl(baseUrl, getIdsByPaginationRouting, pagination.asUrlQueryParts)
    ) {
        contentType(contentType)
    }.body(paginationIdType)

    override suspend fun getById(id: IdType): ObjectType? = httpClient.get(
        buildStandardUrl(
            baseUrl,
            getByIdRouting,
            mapOf(
                idParameterName to idSerializer(id)
            )
        )
    ) {
        contentType(contentType)
    }.takeIf { it.status != HttpStatusCode.NoContent } ?.body<ObjectType>(objectType)

    override suspend fun contains(id: IdType): Boolean = httpClient.get(
        buildStandardUrl(
            baseUrl,
            containsRouting,
            mapOf(
                idParameterName to idSerializer(id)
            )
        )
    ) {
        contentType(contentType)
    }.body()

    override suspend fun getAll(): Map<IdType, ObjectType> = httpClient.get(
        buildStandardUrl(
            baseUrl,
            getAllRoute
        )
    ) {
        contentType(contentType)
    }.body(mapTypeInfo)

    override suspend fun count(): Long = httpClient.get(
        buildStandardUrl(
            baseUrl,
            countRouting
        )
    ) {
        contentType(contentType)
    }.body()
}

inline fun <reified ObjectType, IdType> KtorReadCRUDRepoClient(
    baseUrl: String,
    httpClient: HttpClient,
    contentType: ContentType,
    noinline idSerializer: suspend (IdType) -> String
) = KtorReadCRUDRepoClient<ObjectType, IdType>(
    baseUrl,
    httpClient,
    typeInfo<ObjectType>(),
    typeInfo<PaginationResult<ObjectType>>(),
    typeInfo<PaginationResult<IdType>>(),
    contentType,
    typeInfo<Map<IdType, ObjectType>>(),
    idSerializer
)

inline fun <reified ObjectType, IdType> KtorReadCRUDRepoClient(
    baseUrl: String,
    httpClient: HttpClient,
    idsSerializer: KSerializer<IdType>,
    serialFormat: StringFormat,
    contentType: ContentType,
) = KtorReadCRUDRepoClient<ObjectType, IdType>(baseUrl, httpClient, contentType) {
    serialFormat.encodeToString(idsSerializer, it)
}

inline fun <reified ObjectType, IdType> KtorReadCRUDRepoClient(
    baseUrl: String,
    httpClient: HttpClient,
    idsSerializer: KSerializer<IdType>,
    serialFormat: BinaryFormat,
    contentType: ContentType,
) = KtorReadCRUDRepoClient<ObjectType, IdType>(baseUrl, httpClient, contentType) {
    serialFormat.encodeHex(idsSerializer, it)
}
