package dev.inmo.micro_utils.repos.ktor.server.crud

import dev.inmo.micro_utils.ktor.common.StandardKtorSerialFormat
import dev.inmo.micro_utils.ktor.common.standardKtorSerialFormat
import dev.inmo.micro_utils.ktor.server.UnifiedRouter
import dev.inmo.micro_utils.ktor.server.standardKtorSerialFormatContentType
import dev.inmo.micro_utils.repos.StandardCRUDRepo
import io.ktor.http.ContentType
import io.ktor.routing.Route
import io.ktor.routing.route
import kotlinx.serialization.KSerializer

fun <ObjectType, IdType, InputValue> Route.configureStandardCrudRepoRoutes(
    baseSubpart: String,
    originalRepo: StandardCRUDRepo<ObjectType, IdType, InputValue>,
    objectsSerializer: KSerializer<ObjectType>,
    objectsNullableSerializer: KSerializer<ObjectType?>,
    inputsSerializer: KSerializer<InputValue>,
    idsSerializer: KSerializer<IdType>,
    unifiedRouter: UnifiedRouter
) {
    route(baseSubpart) {
        configureReadStandardCrudRepoRoutes(originalRepo, objectsSerializer, objectsNullableSerializer, idsSerializer, unifiedRouter)
        configureWriteStandardCrudRepoRoutes(originalRepo, objectsSerializer, objectsNullableSerializer, inputsSerializer, idsSerializer, unifiedRouter)
    }
}

fun <ObjectType, IdType, InputValue> Route.configureStandardCrudRepoRoutes(
    baseSubpart: String,
    originalRepo: StandardCRUDRepo<ObjectType, IdType, InputValue>,
    objectsSerializer: KSerializer<ObjectType>,
    objectsNullableSerializer: KSerializer<ObjectType?>,
    inputsSerializer: KSerializer<InputValue>,
    idsSerializer: KSerializer<IdType>,
    serialFormat: StandardKtorSerialFormat = standardKtorSerialFormat,
    serialFormatContentType: ContentType = standardKtorSerialFormatContentType
) = configureStandardCrudRepoRoutes(
    baseSubpart, originalRepo, objectsSerializer, objectsNullableSerializer, inputsSerializer, idsSerializer, UnifiedRouter(serialFormat, serialFormatContentType)
)
