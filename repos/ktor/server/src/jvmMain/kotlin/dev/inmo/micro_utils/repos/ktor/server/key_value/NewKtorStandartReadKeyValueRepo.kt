package dev.inmo.micro_utils.repos.ktor.server.key_value

import dev.inmo.micro_utils.ktor.common.*
import dev.inmo.micro_utils.ktor.server.*
import dev.inmo.micro_utils.pagination.PaginationResult
import dev.inmo.micro_utils.pagination.extractPagination
import dev.inmo.micro_utils.repos.ReadStandardCRUDRepo
import dev.inmo.micro_utils.repos.ReadStandardKeyValueRepo
import dev.inmo.micro_utils.repos.ktor.common.idParameterName
import dev.inmo.micro_utils.repos.ktor.common.key_value.*
import dev.inmo.micro_utils.repos.ktor.common.valueParameterName
import io.ktor.http.*
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.response.responseType
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.util.InternalAPI
import io.ktor.util.reflect.typeInfo
import kotlinx.serialization.*
import kotlinx.serialization.builtins.serializer

@OptIn(InternalAPI::class)
inline fun <reified Key, reified Value> Route.configureReadStandardKeyValueRepoRoutes (
    originalRepo: ReadStandardKeyValueRepo<Key, Value>,
    noinline idDeserializer: suspend (String) -> Key,
    noinline valueDeserializer: suspend (String) -> Value
) {
    get(getRoute) {
        val key = idDeserializer(
            call.getQueryParameterOrSendError(idParameterName) ?: return@get
        )

        originalRepo.get(key) ?.let {
            call.respond(it)
        } ?: call.respond(HttpStatusCode.NoContent)
    }

    val paginationWithValuesTypeInfo = typeInfo<PaginationResult<Value>>()
    get(valuesRoute) {
        val pagination = call.request.queryParameters.extractPagination
        val reversed = call.getQueryParameter(reversedParameterName) ?.toBoolean() ?: false

        call.response.responseType = paginationWithValuesTypeInfo
        call.response.pipeline.execute(call, originalRepo.values(pagination, reversed) as Any)
    }

    get(keysRoute) {
        val pagination = call.request.queryParameters.extractPagination
        val reversed = call.getQueryParameterOrSendError(reversedParameterName) ?.toBoolean() ?: false
        val value = call.getQueryParameter(valueParameterName) ?.let {
            valueDeserializer(it)
        }

        call.respond(
            value ?.let { originalRepo.keys(value, pagination, reversed) } ?: originalRepo.keys(pagination, reversed)
        )
    }

    get(containsRoute) {
        val key = idDeserializer(
            call.getQueryParameterOrSendError(idParameterName) ?: return@get
        )

        call.respond(
            originalRepo.contains(key)
        )
    }

    get(countRoute) {
        call.respond(originalRepo.count())
    }
}

inline fun <reified Key, reified Value> Route.configureReadStandardKeyValueRepoRoutes(
    originalRepo: ReadStandardKeyValueRepo<Key, Value>,
    idsSerializer: DeserializationStrategy<Key>,
    valueSerializer: DeserializationStrategy<Value>,
    serialFormat: StringFormat
) = configureReadStandardKeyValueRepoRoutes(
    originalRepo,
    {
        serialFormat.decodeFromString(idsSerializer, it.decodeURLQueryComponent())
    },
    {
        serialFormat.decodeFromString(valueSerializer, it.decodeURLQueryComponent())
    }
)

inline fun <reified Key, reified Value> Route.configureReadStandardKeyValueRepoRoutes(
    originalRepo: ReadStandardKeyValueRepo<Key, Value>,
    idsSerializer: DeserializationStrategy<Key>,
    valueSerializer: DeserializationStrategy<Value>,
    serialFormat: BinaryFormat
) = configureReadStandardKeyValueRepoRoutes(
    originalRepo,
    {
        serialFormat.decodeHex(idsSerializer, it)
    },
    {
        serialFormat.decodeHex(valueSerializer, it)
    }
)

