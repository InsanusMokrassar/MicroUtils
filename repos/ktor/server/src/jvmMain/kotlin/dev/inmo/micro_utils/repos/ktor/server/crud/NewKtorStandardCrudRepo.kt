package dev.inmo.micro_utils.repos.ktor.server.crud

import dev.inmo.micro_utils.ktor.common.*
import dev.inmo.micro_utils.ktor.server.UnifiedRouter
import dev.inmo.micro_utils.ktor.server.standardKtorSerialFormatContentType
import dev.inmo.micro_utils.repos.StandardCRUDRepo
import io.ktor.http.ContentType
import io.ktor.server.routing.Route
import io.ktor.server.routing.route
import kotlinx.serialization.*

inline fun <reified ObjectType : Any, reified IdType : Any, reified InputValue : Any> Route.configureStandardCrudRepoRoutes(
    originalRepo: StandardCRUDRepo<ObjectType, IdType, InputValue>,
    noinline idDeserializer: suspend (String) -> IdType
) {
    configureReadStandardCrudRepoRoutes(originalRepo, idDeserializer)
    configureWriteStandardCrudRepoRoutes(originalRepo)
}

inline fun <reified ObjectType : Any, reified IdType : Any, reified InputValue : Any> Route.configureStandardCrudRepoRoutes(
    originalRepo: StandardCRUDRepo<ObjectType, IdType, InputValue>,
    idsSerializer: KSerializer<IdType>,
    serialFormat: StringFormat
) = configureStandardCrudRepoRoutes(originalRepo) {
    serialFormat.decodeFromString(idsSerializer, it)
}

inline fun <reified ObjectType : Any, reified IdType : Any, reified InputValue : Any> Route.configureStandardCrudRepoRoutes(
    originalRepo: StandardCRUDRepo<ObjectType, IdType, InputValue>,
    idsSerializer: KSerializer<IdType>,
    serialFormat: BinaryFormat
) = configureStandardCrudRepoRoutes(originalRepo) {
    serialFormat.decodeHex(idsSerializer, it)
}
