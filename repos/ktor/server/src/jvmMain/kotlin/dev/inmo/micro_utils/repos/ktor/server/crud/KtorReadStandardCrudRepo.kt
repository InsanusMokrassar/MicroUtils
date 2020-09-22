package dev.inmo.micro_utils.repos.ktor.server.crud

import dev.inmo.micro_utils.ktor.server.decodeUrlQueryValueOrSendError
import dev.inmo.micro_utils.ktor.server.unianswer
import dev.inmo.micro_utils.repos.ktor.common.crud.containsRouting
import dev.inmo.micro_utils.repos.ktor.common.crud.getByIdRouting
import dev.inmo.micro_utils.repos.ktor.common.crud.getByPaginationRouting
import dev.inmo.micro_utils.pagination.PaginationResult
import dev.inmo.micro_utils.pagination.extractPagination
import dev.inmo.micro_utils.repos.ReadStandardCRUDRepo
import io.ktor.application.call
import io.ktor.routing.Route
import io.ktor.routing.get
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer

fun <ObjectType, IdType> Route.configureReadStandardCrudRepoRoutes(
    originalRepo: ReadStandardCRUDRepo<ObjectType, IdType>,
    objectsSerializer: KSerializer<ObjectType>,
    objectsNullableSerializer: KSerializer<ObjectType?>,
    idsSerializer: KSerializer<IdType>
) {
    val paginationResultSerializer = PaginationResult.serializer(objectsSerializer)

    get(getByPaginationRouting) {
        val pagination = call.request.queryParameters.extractPagination

        call.unianswer(
            paginationResultSerializer,
            originalRepo.getByPagination(pagination)
        )
    }

    get(getByIdRouting) {
        val id = call.decodeUrlQueryValueOrSendError(
            "id",
            idsSerializer
        ) ?: return@get

        call.unianswer(
            objectsNullableSerializer,
            originalRepo.getById(id)
        )
    }

    get(containsRouting) {
        val id = call.decodeUrlQueryValueOrSendError(
            "id",
            idsSerializer
        ) ?: return@get

        call.unianswer(
            Boolean.serializer(),
            originalRepo.contains(id)
        )
    }
}
