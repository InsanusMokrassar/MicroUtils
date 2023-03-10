package dev.inmo.micro_utils.repos.ktor.server.crud

import dev.inmo.micro_utils.ktor.common.decodeHex
import dev.inmo.micro_utils.ktor.server.*
import dev.inmo.micro_utils.pagination.extractPagination
import dev.inmo.micro_utils.repos.ReadCRUDRepo
import dev.inmo.micro_utils.repos.ktor.common.countRouting
import dev.inmo.micro_utils.repos.ktor.common.crud.*
import dev.inmo.micro_utils.repos.ktor.common.getAllRoute
import dev.inmo.micro_utils.repos.ktor.common.idParameterName
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import kotlinx.serialization.*

inline fun <reified ObjectType, reified IdType> Route.configureReadCRUDRepoRoutes(
    originalRepo: ReadCRUDRepo<ObjectType, IdType>,
    noinline idDeserializer: suspend (String) -> IdType
) {
    get(getByPaginationRouting) {
        val pagination = call.request.queryParameters.extractPagination

        call.respond(originalRepo.getByPagination(pagination))
    }
    get(getIdsByPaginationRouting) {
        val pagination = call.request.queryParameters.extractPagination

        call.respond(originalRepo.getIdsByPagination(pagination))
    }

    get(getByIdRouting) {
        val id = idDeserializer(
            call.getQueryParameterOrSendError(idParameterName) ?: return@get
        )

        val result = originalRepo.getById(id)

        if (result == null) {
            call.respond(HttpStatusCode.NoContent)
        } else {
            call.respond(result)
        }
    }

    get(containsRouting) {
        val id = idDeserializer(
            call.getQueryParameterOrSendError(idParameterName) ?: return@get
        )

        call.respond(
            originalRepo.contains(id)
        )
    }

    get(getAllRoute) {
        call.respond(originalRepo.getAll())
    }

    get(countRouting) {
        call.respond(
            originalRepo.count()
        )
    }
}

inline fun <reified ObjectType, reified IdType> Route.configureReadCRUDRepoRoutes(
    originalRepo: ReadCRUDRepo<ObjectType, IdType>,
    idsSerializer: KSerializer<IdType>,
    serialFormat: StringFormat
) = configureReadCRUDRepoRoutes(originalRepo) {
    serialFormat.decodeFromString(idsSerializer, it)
}

inline fun <reified ObjectType, reified IdType> Route.configureReadCRUDRepoRoutes(
    originalRepo: ReadCRUDRepo<ObjectType, IdType>,
    idsSerializer: KSerializer<IdType>,
    serialFormat: BinaryFormat
) = configureReadCRUDRepoRoutes(originalRepo) {
    serialFormat.decodeHex(idsSerializer, it)
}
