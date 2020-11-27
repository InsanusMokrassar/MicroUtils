package dev.inmo.micro_utils.repos.ktor.client.crud

import dev.inmo.micro_utils.ktor.client.*
import dev.inmo.micro_utils.ktor.common.*
import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.repos.ReadStandardCRUDRepo
import dev.inmo.micro_utils.repos.ktor.common.crud.*
import io.ktor.client.HttpClient
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer

class KtorReadStandardCrudRepo<ObjectType, IdType> (
    private val baseUrl: String,
    private val unifiedRequester: UnifiedRequester,
    private val objectsSerializer: KSerializer<ObjectType>,
    private val objectsSerializerNullable: KSerializer<ObjectType?>,
    private val idsSerializer: KSerializer<IdType>
) : ReadStandardCRUDRepo<ObjectType, IdType> {
    private val paginationResultSerializer = PaginationResult.serializer(objectsSerializer)

    constructor(
        baseUrl: String,
        client: HttpClient,
        objectsSerializer: KSerializer<ObjectType>,
        objectsSerializerNullable: KSerializer<ObjectType?>,
        idsSerializer: KSerializer<IdType>,
        serialFormat: StandardKtorSerialFormat = standardKtorSerialFormat
    ) : this (
        baseUrl, UnifiedRequester(client, serialFormat), objectsSerializer, objectsSerializerNullable, idsSerializer
    )

    override suspend fun getByPagination(pagination: Pagination): PaginationResult<ObjectType> = unifiedRequester.uniget(
        buildStandardUrl(baseUrl, getByPaginationRouting, pagination.asUrlQueryParts),
        paginationResultSerializer
    )

    override suspend fun getById(id: IdType): ObjectType? = unifiedRequester.uniget(
        buildStandardUrl(
            baseUrl,
            getByIdRouting,
            mapOf(
                "id" to unifiedRequester.encodeUrlQueryValue(idsSerializer, id)
            )
        ),
        objectsSerializerNullable
    )

    override suspend fun contains(id: IdType): Boolean = unifiedRequester.uniget(
        buildStandardUrl(
            baseUrl,
            containsRouting,
            mapOf(
                "id" to unifiedRequester.encodeUrlQueryValue(idsSerializer, id)
            )
        ),
        Boolean.serializer()
    )

    override suspend fun count(): Long = unifiedRequester.uniget(
        buildStandardUrl(
            baseUrl,
            countRouting
        ),
        Long.serializer()
    )
}
