package dev.inmo.micro_utils.repos.ktor.client.crud

import dev.inmo.micro_utils.ktor.common.*
import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.repos.ReadStandardCRUDRepo
import dev.inmo.micro_utils.repos.ktor.common.crud.*
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.util.reflect.TypeInfo
import io.ktor.util.reflect.typeInfo
import kotlinx.serialization.*

class KtorReadStandardCrudRepoClient<ObjectType, IdType> (
    private val baseUrl: String,
    private val httpClient: HttpClient,
    private val objectType: TypeInfo,
    private val idSerializer: suspend (IdType) -> String
) : ReadStandardCRUDRepo<ObjectType, IdType> {
    override suspend fun getByPagination(pagination: Pagination): PaginationResult<ObjectType> = httpClient.get(
        buildStandardUrl(baseUrl, getByPaginationRouting, pagination.asUrlQueryParts)
    ).body()

    override suspend fun getById(id: IdType): ObjectType? = httpClient.get(
        buildStandardUrl(
            baseUrl,
            getByIdRouting,
            mapOf(
                "id" to idSerializer(id)
            )
        )
    ).takeIf { it.status != HttpStatusCode.NoContent } ?.body<ObjectType>(objectType)

    override suspend fun contains(id: IdType): Boolean = httpClient.get(
        buildStandardUrl(
            baseUrl,
            containsRouting,
            mapOf(
                "id" to idSerializer(id)
            )
        )
    ).body()

    override suspend fun count(): Long = httpClient.get(
        buildStandardUrl(
            baseUrl,
            countRouting
        )
    ).body()
}

inline fun <reified ObjectType, IdType> KtorReadStandardCrudRepoClient(
    baseUrl: String,
    httpClient: HttpClient,
    noinline idSerializer: suspend (IdType) -> String
) = KtorReadStandardCrudRepoClient<ObjectType, IdType>(
    baseUrl,
    httpClient,
    typeInfo<ObjectType>(),
    idSerializer
)

inline fun <reified ObjectType, IdType> KtorReadStandardCrudRepoClient(
    baseUrl: String,
    httpClient: HttpClient,
    idsSerializer: KSerializer<IdType>,
    serialFormat: StringFormat
) = KtorReadStandardCrudRepoClient<ObjectType, IdType>(baseUrl, httpClient) {
    serialFormat.encodeToString(idsSerializer, it)
}

inline fun <reified ObjectType, IdType> KtorReadStandardCrudRepoClient(
    baseUrl: String,
    httpClient: HttpClient,
    idsSerializer: KSerializer<IdType>,
    serialFormat: BinaryFormat
) = KtorReadStandardCrudRepoClient<ObjectType, IdType>(baseUrl, httpClient) {
    serialFormat.encodeHex(idsSerializer, it)
}
