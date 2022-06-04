package dev.inmo.micro_utils.repos.ktor.server.crud

import dev.inmo.micro_utils.ktor.common.StandardKtorSerialFormat
import dev.inmo.micro_utils.ktor.common.standardKtorSerialFormat
import dev.inmo.micro_utils.ktor.server.*
import dev.inmo.micro_utils.pagination.PaginationResult
import dev.inmo.micro_utils.pagination.extractPagination
import dev.inmo.micro_utils.repos.ReadCRUDRepo
import dev.inmo.micro_utils.repos.ktor.common.countRouting
import dev.inmo.micro_utils.repos.ktor.common.crud.*
import io.ktor.http.ContentType
import io.ktor.server.application.call
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer

fun <ObjectType, IdType> Route.configureReadCRUDRepoRoutes(
    originalRepo: ReadCRUDRepo<ObjectType, IdType>,
    objectsSerializer: KSerializer<ObjectType>,
    objectsNullableSerializer: KSerializer<ObjectType?>,
    idsSerializer: KSerializer<IdType>,
    unifiedRouter: UnifiedRouter
) {
    val paginationResultSerializer = PaginationResult.serializer(objectsSerializer)

    get(getByPaginationRouting) {
        unifiedRouter.apply {
            val pagination = call.request.queryParameters.extractPagination

            unianswer(
                paginationResultSerializer,
                originalRepo.getByPagination(pagination)
            )
        }
    }

    get(getByIdRouting) {
        unifiedRouter.apply {
            val id = decodeUrlQueryValueOrSendError(
                "id",
                idsSerializer
            ) ?: return@get

            unianswer(
                objectsNullableSerializer,
                originalRepo.getById(id)
            )
        }
    }

    get(containsRouting) {
        unifiedRouter.apply {
            val id = decodeUrlQueryValueOrSendError(
                "id",
                idsSerializer
            ) ?: return@get

            unianswer(
                Boolean.serializer(),
                originalRepo.contains(id)
            )
        }
    }

    get(countRouting) {
        unifiedRouter.apply {
            unianswer(
                Long.serializer(),
                originalRepo.count()
            )
        }
    }
}

inline fun <ObjectType, IdType> Route.configureReadCRUDRepoRoutes(
    originalRepo: ReadCRUDRepo<ObjectType, IdType>,
    objectsSerializer: KSerializer<ObjectType>,
    objectsNullableSerializer: KSerializer<ObjectType?>,
    idsSerializer: KSerializer<IdType>,
    serialFormat: StandardKtorSerialFormat = standardKtorSerialFormat,
    serialFormatContentType: ContentType = standardKtorSerialFormatContentType
) = configureReadCRUDRepoRoutes(originalRepo, objectsSerializer, objectsNullableSerializer, idsSerializer, UnifiedRouter(serialFormat, serialFormatContentType))
