package dev.inmo.micro_utils.repos.ktor.server.crud

import dev.inmo.micro_utils.ktor.common.StandardKtorSerialFormat
import dev.inmo.micro_utils.ktor.common.standardKtorSerialFormat
import dev.inmo.micro_utils.ktor.server.UnifiedRouter
import dev.inmo.micro_utils.ktor.server.standardKtorSerialFormatContentType
import dev.inmo.micro_utils.repos.CRUDRepo
import io.ktor.http.ContentType
import io.ktor.server.routing.Route
import io.ktor.server.routing.route
import kotlinx.serialization.KSerializer

fun <ObjectType, IdType, InputValue> Route.configureCRUDRepoRoutes(
    baseSubpart: String,
    originalRepo: CRUDRepo<ObjectType, IdType, InputValue>,
    objectsSerializer: KSerializer<ObjectType>,
    objectsNullableSerializer: KSerializer<ObjectType?>,
    inputsSerializer: KSerializer<InputValue>,
    idsSerializer: KSerializer<IdType>,
    unifiedRouter: UnifiedRouter
) {
    route(baseSubpart) {
        configureReadCRUDRepoRoutes(originalRepo, objectsSerializer, objectsNullableSerializer, idsSerializer, unifiedRouter)
        configureWriteCRUDRepoRoutes(originalRepo, objectsSerializer, objectsNullableSerializer, inputsSerializer, idsSerializer, unifiedRouter)
    }
}

fun <ObjectType, IdType, InputValue> Route.configureCRUDRepoRoutes(
    baseSubpart: String,
    originalRepo: CRUDRepo<ObjectType, IdType, InputValue>,
    objectsSerializer: KSerializer<ObjectType>,
    objectsNullableSerializer: KSerializer<ObjectType?>,
    inputsSerializer: KSerializer<InputValue>,
    idsSerializer: KSerializer<IdType>,
    serialFormat: StandardKtorSerialFormat = standardKtorSerialFormat,
    serialFormatContentType: ContentType = standardKtorSerialFormatContentType
) = configureCRUDRepoRoutes(
    baseSubpart, originalRepo, objectsSerializer, objectsNullableSerializer, inputsSerializer, idsSerializer, UnifiedRouter(serialFormat, serialFormatContentType)
)
