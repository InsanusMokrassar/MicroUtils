package dev.inmo.micro_utils.repos.ktor.server.one_to_many

import dev.inmo.micro_utils.ktor.common.StandardKtorSerialFormat
import dev.inmo.micro_utils.ktor.common.standardKtorSerialFormat
import dev.inmo.micro_utils.ktor.server.*
import dev.inmo.micro_utils.pagination.PaginationResult
import dev.inmo.micro_utils.pagination.extractPagination
import dev.inmo.micro_utils.repos.ReadOneToManyKeyValueRepo
import dev.inmo.micro_utils.repos.ktor.common.keyParameterName
import dev.inmo.micro_utils.repos.ktor.common.one_to_many.*
import dev.inmo.micro_utils.repos.ktor.common.valueParameterName
import dev.inmo.micro_utils.repos.ktor.common.reversedParameterName
import io.ktor.http.ContentType
import io.ktor.server.application.call
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer

fun <Key, Value> Route.configureOneToManyReadKeyValueRepoRoutes(
    originalRepo: ReadOneToManyKeyValueRepo<Key, Value>,
    keySerializer: KSerializer<Key>,
    valueSerializer: KSerializer<Value>,
    unifiedRouter: UnifiedRouter
) {
    val paginationKeyResult = PaginationResult.serializer(keySerializer)
    val paginationValueResult = PaginationResult.serializer(valueSerializer)

    get(getRoute) {
        unifiedRouter.apply {
            val pagination = call.request.queryParameters.extractPagination
            val key = decodeUrlQueryValueOrSendError(
                keyParameterName,
                keySerializer
            ) ?: return@get
            val reversed = decodeUrlQueryValue(
                reversedParameterName,
                Boolean.serializer()
            ) ?: false

            unianswer(
                paginationValueResult,
                originalRepo.get(key, pagination, reversed)
            )
        }
    }

    get(keysRoute) {
        unifiedRouter.apply {
            val pagination = call.request.queryParameters.extractPagination
            val reversed = decodeUrlQueryValue(
                reversedParameterName,
                Boolean.serializer()
            ) ?: false
            val value: Value? = decodeUrlQueryValue(
                valueParameterName,
                valueSerializer
            )

            unianswer(
                paginationKeyResult,
                value?.let { originalRepo.keys(value, pagination, reversed) } ?: originalRepo.keys(pagination, reversed)
            )
        }
    }

    get(containsByKeyRoute) {
        unifiedRouter.apply {
            val key = decodeUrlQueryValueOrSendError(
                keyParameterName,
                keySerializer
            ) ?: return@get

            unianswer(
                Boolean.serializer(),
                originalRepo.contains(key)
            )
        }
    }

    get(containsByKeyValueRoute) {
        unifiedRouter.apply {
            val key = decodeUrlQueryValueOrSendError(
                keyParameterName,
                keySerializer
            ) ?: return@get
            val value = decodeUrlQueryValueOrSendError(
                valueParameterName,
                valueSerializer
            ) ?: return@get

            unianswer(
                Boolean.serializer(),
                originalRepo.contains(key, value)
            )
        }
    }

    get(countByKeyRoute) {
        unifiedRouter.apply {
            val key = decodeUrlQueryValueOrSendError(
                keyParameterName,
                keySerializer
            ) ?: return@get

            unianswer(
                Long.serializer(),
                originalRepo.count(key)
            )
        }
    }

    get(countRoute) {
        unifiedRouter.apply {
            unianswer(
                Long.serializer(),
                originalRepo.count()
            )
        }
    }
}

inline fun <Key, Value> Route.configureOneToManyReadKeyValueRepoRoutes(
    originalRepo: ReadOneToManyKeyValueRepo<Key, Value>,
    keySerializer: KSerializer<Key>,
    valueSerializer: KSerializer<Value>,
    serialFormat: StandardKtorSerialFormat = standardKtorSerialFormat,
    serialFormatContentType: ContentType = standardKtorSerialFormatContentType
) = configureOneToManyReadKeyValueRepoRoutes(originalRepo, keySerializer, valueSerializer, UnifiedRouter(serialFormat, serialFormatContentType))
