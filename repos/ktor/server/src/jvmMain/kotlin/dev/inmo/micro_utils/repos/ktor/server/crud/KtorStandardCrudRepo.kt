package dev.inmo.micro_utils.repos.ktor.server.crud

import dev.inmo.micro_utils.repos.StandardCRUDRepo
import io.ktor.routing.Route
import io.ktor.routing.route
import kotlinx.serialization.KSerializer

fun <ObjectType, IdType, InputValue> Route.configureStandardCrudRepoRoutes(
    baseSubpart: String,
    originalRepo: StandardCRUDRepo<ObjectType, IdType, InputValue>,
    objectsSerializer: KSerializer<ObjectType>,
    objectsNullableSerializer: KSerializer<ObjectType?>,
    inputsSerializer: KSerializer<InputValue>,
    idsSerializer: KSerializer<IdType>
) {
    route(baseSubpart) {
        configureReadStandardCrudRepoRoutes(originalRepo, objectsSerializer, objectsNullableSerializer, idsSerializer)
        configureWriteStandardCrudRepoRoutes(originalRepo, objectsSerializer, objectsNullableSerializer, inputsSerializer, idsSerializer)
    }
}
