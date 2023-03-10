package dev.inmo.micro_utils.repos.ktor.server.key.value

import dev.inmo.micro_utils.ktor.common.*
import dev.inmo.micro_utils.ktor.server.*
import dev.inmo.micro_utils.pagination.PaginationResult
import dev.inmo.micro_utils.pagination.extractPagination
import dev.inmo.micro_utils.repos.ReadKeyValueRepo
import dev.inmo.micro_utils.repos.ktor.common.*
import dev.inmo.micro_utils.repos.ktor.common.containsRoute
import dev.inmo.micro_utils.repos.ktor.common.countRoute
import dev.inmo.micro_utils.repos.ktor.common.keyParameterName
import dev.inmo.micro_utils.repos.ktor.common.key_value.*
import dev.inmo.micro_utils.repos.ktor.common.reversedParameterName
import io.ktor.http.*
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.util.InternalAPI
import io.ktor.util.reflect.typeInfo
import kotlinx.serialization.*

@OptIn(InternalAPI::class)
inline fun <reified Key, reified Value> Route.configureReadKeyValueRepoRoutes (
    originalRepo: ReadKeyValueRepo<Key, Value>,
    noinline idDeserializer: suspend (String) -> Key,
    noinline valueDeserializer: suspend (String) -> Value
) {
    val paginationWithValuesTypeInfo = typeInfo<PaginationResult<Value>>()
    val paginationWithKeysTypeInfo = typeInfo<PaginationResult<Key>>()

    get(getRoute) {
        val key = idDeserializer(
            call.getQueryParameterOrSendError(keyParameterName) ?: return@get
        )

        originalRepo.get(key) ?.let {
            call.respond(it)
        } ?: call.respond(HttpStatusCode.NoContent)
    }

    get(valuesRoute) {
        val pagination = call.request.queryParameters.extractPagination
        val reversed = call.getQueryParameter(reversedParameterName) ?.toBoolean() ?: false

        call.respond(
            originalRepo.values(pagination, reversed),
            paginationWithValuesTypeInfo
        )
    }

    get(keysRoute) {
        val pagination = call.request.queryParameters.extractPagination
        val reversed = call.getQueryParameter(reversedParameterName) ?.toBoolean() ?: false
        val value = call.getQueryParameter(valueParameterName) ?.let {
            valueDeserializer(it)
        }

        call.respond(
            value ?.let { originalRepo.keys(value, pagination, reversed) } ?: originalRepo.keys(pagination, reversed),
            paginationWithKeysTypeInfo
        )
    }

    get(containsRoute) {
        val key = idDeserializer(
            call.getQueryParameterOrSendError(keyParameterName) ?: return@get
        )

        call.respond(originalRepo.contains(key))
    }

    get(getAllRoute) {
        call.respond(originalRepo.getAll())
    }

    get(countRoute) {
        call.respond(originalRepo.count())
    }
}

inline fun <reified Key, reified Value> Route.configureReadKeyValueRepoRoutes(
    originalRepo: ReadKeyValueRepo<Key, Value>,
    idsSerializer: DeserializationStrategy<Key>,
    valueSerializer: DeserializationStrategy<Value>,
    serialFormat: StringFormat
) = configureReadKeyValueRepoRoutes(
    originalRepo,
    {
        serialFormat.decodeFromString(idsSerializer, it.decodeURLQueryComponent())
    },
    {
        serialFormat.decodeFromString(valueSerializer, it.decodeURLQueryComponent())
    }
)

inline fun <reified Key, reified Value> Route.configureReadKeyValueRepoRoutes(
    originalRepo: ReadKeyValueRepo<Key, Value>,
    idsSerializer: DeserializationStrategy<Key>,
    valueSerializer: DeserializationStrategy<Value>,
    serialFormat: BinaryFormat
) = configureReadKeyValueRepoRoutes(
    originalRepo,
    {
        serialFormat.decodeHex(idsSerializer, it)
    },
    {
        serialFormat.decodeHex(valueSerializer, it)
    }
)

