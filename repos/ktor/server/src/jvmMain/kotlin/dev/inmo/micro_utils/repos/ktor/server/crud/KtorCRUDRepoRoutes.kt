package dev.inmo.micro_utils.repos.ktor.server.crud

import dev.inmo.micro_utils.ktor.common.*
import dev.inmo.micro_utils.repos.CRUDRepo
import io.ktor.server.routing.Routing
import kotlinx.serialization.*

inline fun <reified ObjectType : Any, reified IdType : Any, reified InputValue : Any> Routing.configureCRUDRepoRoutes(
    originalRepo: CRUDRepo<ObjectType, IdType, InputValue>,
    noinline idDeserializer: suspend (String) -> IdType
) {
    configureReadCRUDRepoRoutes(originalRepo, idDeserializer)
    configureWriteCRUDRepoRoutes(originalRepo)
}

inline fun <reified ObjectType : Any, reified IdType : Any, reified InputValue : Any> Routing.configureCRUDRepoRoutes(
    originalRepo: CRUDRepo<ObjectType, IdType, InputValue>,
    idsSerializer: KSerializer<IdType>,
    serialFormat: StringFormat
) = configureCRUDRepoRoutes(originalRepo) {
    serialFormat.decodeFromString(idsSerializer, it)
}

inline fun <reified ObjectType : Any, reified IdType : Any, reified InputValue : Any> Routing.configureCRUDRepoRoutes(
    originalRepo: CRUDRepo<ObjectType, IdType, InputValue>,
    idsSerializer: KSerializer<IdType>,
    serialFormat: BinaryFormat
) = configureCRUDRepoRoutes(originalRepo) {
    serialFormat.decodeHex(idsSerializer, it)
}
