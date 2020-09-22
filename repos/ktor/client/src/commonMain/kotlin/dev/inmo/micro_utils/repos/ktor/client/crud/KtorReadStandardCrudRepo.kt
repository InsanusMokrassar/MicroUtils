package dev.inmo.micro_utils.repos.ktor.client.crud

import dev.inmo.micro_utils.ktor.client.encodeUrlQueryValue
import dev.inmo.micro_utils.ktor.client.uniget
import dev.inmo.micro_utils.ktor.common.buildStandardUrl
import dev.inmo.micro_utils.pagination.Pagination
import dev.inmo.micro_utils.pagination.PaginationResult
import dev.inmo.micro_utils.pagination.asUrlQueryParts
import dev.inmo.micro_utils.repos.ReadStandardCRUDRepo
import dev.inmo.micro_utils.repos.ktor.common.crud.*
import io.ktor.client.HttpClient
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer

class KtorReadStandardCrudRepo<ObjectType, IdType> (
    private val baseUrl: String,
    private val client: HttpClient = HttpClient(),
    private val objectsSerializer: KSerializer<ObjectType>,
    private val objectsSerializerNullable: KSerializer<ObjectType?>,
    private val idsSerializer: KSerializer<IdType>
) : ReadStandardCRUDRepo<ObjectType, IdType> {
    private val paginationResultSerializer = PaginationResult.serializer(objectsSerializer)

    override suspend fun getByPagination(pagination: Pagination): PaginationResult<ObjectType> = client.uniget(
        buildStandardUrl(baseUrl, getByPaginationRouting, pagination.asUrlQueryParts),
        paginationResultSerializer
    )

    override suspend fun getById(id: IdType): ObjectType? = client.uniget(
        buildStandardUrl(
            baseUrl,
            getByIdRouting,
            mapOf(
                "id" to idsSerializer.encodeUrlQueryValue(id)
            )
        ),
        objectsSerializerNullable
    )

    override suspend fun contains(id: IdType): Boolean = client.uniget(
        buildStandardUrl(
            baseUrl,
            containsRouting,
            mapOf(
                "id" to idsSerializer.encodeUrlQueryValue(id)
            )
        ),
        Boolean.serializer()
    )
}
