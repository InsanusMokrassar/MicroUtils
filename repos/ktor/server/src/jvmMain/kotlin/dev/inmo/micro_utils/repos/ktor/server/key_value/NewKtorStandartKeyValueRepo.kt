package dev.inmo.micro_utils.repos.ktor.server.key_value

import dev.inmo.micro_utils.ktor.common.*
import dev.inmo.micro_utils.ktor.server.*
import dev.inmo.micro_utils.pagination.PaginationResult
import dev.inmo.micro_utils.pagination.extractPagination
import dev.inmo.micro_utils.repos.StandardCRUDRepo
import dev.inmo.micro_utils.repos.StandardKeyValueRepo
import dev.inmo.micro_utils.repos.ktor.common.idParameterName
import dev.inmo.micro_utils.repos.ktor.common.key_value.*
import dev.inmo.micro_utils.repos.ktor.common.valueParameterName
import io.ktor.http.*
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import kotlinx.serialization.*
import kotlinx.serialization.builtins.serializer

inline fun <reified Key : Any, reified Value : Any> Route.configureStandardKeyValueRepoRoutes (
    originalRepo: StandardKeyValueRepo<Key, Value>,
    noinline idDeserializer: suspend (String) -> Key,
    noinline valueDeserializer: suspend (String) -> Value
) {
    configureReadStandardKeyValueRepoRoutes(originalRepo, idDeserializer, valueDeserializer)
    configureWriteStandardKeyValueRepoRoutes(originalRepo)
}

inline fun <reified Key : Any, reified Value : Any> Route.configureStandardKeyValueRepoRoutes(
    originalRepo: StandardKeyValueRepo<Key, Value>,
    idsSerializer: DeserializationStrategy<Key>,
    valueSerializer: DeserializationStrategy<Value>,
    serialFormat: StringFormat
) = configureStandardKeyValueRepoRoutes(
    originalRepo,
    {
        serialFormat.decodeFromString(idsSerializer, it.decodeURLQueryComponent())
    },
    {
        serialFormat.decodeFromString(valueSerializer, it.decodeURLQueryComponent())
    }
)

inline fun <reified Key : Any, reified Value : Any> Route.configureStandardKeyValueRepoRoutes(
    originalRepo: StandardKeyValueRepo<Key, Value>,
    idsSerializer: DeserializationStrategy<Key>,
    valueSerializer: DeserializationStrategy<Value>,
    serialFormat: BinaryFormat
) = configureStandardKeyValueRepoRoutes(
    originalRepo,
    {
        serialFormat.decodeHex(idsSerializer, it)
    },
    {
        serialFormat.decodeHex(valueSerializer, it)
    }
)

