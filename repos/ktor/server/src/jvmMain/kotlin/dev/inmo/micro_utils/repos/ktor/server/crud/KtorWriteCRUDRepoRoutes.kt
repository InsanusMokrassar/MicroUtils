package dev.inmo.micro_utils.repos.ktor.server.crud

import dev.inmo.micro_utils.ktor.server.*
import dev.inmo.micro_utils.repos.WriteCRUDRepo
import dev.inmo.micro_utils.repos.ktor.common.crud.*
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

inline fun <reified ObjectType : Any, reified IdType : Any, reified InputValue : Any> Route.configureWriteCRUDRepoRoutes(
    originalRepo: WriteCRUDRepo<ObjectType, IdType, InputValue>
) {
    includeWebsocketHandling(
        newObjectsFlowRouting,
        originalRepo.newObjectsFlow,
    )
    includeWebsocketHandling(
        updatedObjectsFlowRouting,
        originalRepo.updatedObjectsFlow
    )
    includeWebsocketHandling(
        deletedObjectsIdsFlowRouting,
        originalRepo.deletedObjectsIdsFlow
    )

    post(createRouting) {
        call.respond(originalRepo.create(call.receive()))
    }

    post(updateRouting) {
        call.respond(originalRepo.update(call.receive()))
    }

    post(deleteByIdRouting) {
        originalRepo.deleteById(call.receive())
        call.respond(HttpStatusCode.OK)
    }
}
