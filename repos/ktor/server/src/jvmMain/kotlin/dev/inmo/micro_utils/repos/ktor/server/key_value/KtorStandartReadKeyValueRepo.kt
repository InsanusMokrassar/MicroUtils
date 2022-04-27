package dev.inmo.micro_utils.repos.ktor.server.key_value

import dev.inmo.micro_utils.ktor.common.StandardKtorSerialFormat
import dev.inmo.micro_utils.ktor.common.standardKtorSerialFormat
import dev.inmo.micro_utils.ktor.server.*
import dev.inmo.micro_utils.pagination.PaginationResult
import dev.inmo.micro_utils.pagination.extractPagination
import dev.inmo.micro_utils.repos.ReadStandardKeyValueRepo
import dev.inmo.micro_utils.repos.ktor.common.key_value.*
import dev.inmo.micro_utils.repos.ktor.common.valueParameterName
import io.ktor.http.ContentType
import io.ktor.server.application.call
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer

fun <K, V> Route.configureReadStandartKeyValueRepoRoutes (
    originalRepo: ReadStandardKeyValueRepo<K, V>,
    keySerializer: KSerializer<K>,
    valueSerializer: KSerializer<V>,
    valueNullableSerializer: KSerializer<V?>,
    unifiedRouter: UnifiedRouter
) {
    get(getRoute) {
        unifiedRouter.apply {
            val key = decodeUrlQueryValueOrSendError(
                keyParameterName,
                keySerializer
            ) ?: return@get

            unianswer(
                valueNullableSerializer,
                originalRepo.get(key)
            )
        }
    }

    get(valuesRoute) {
        unifiedRouter.apply {
            val parination = call.request.queryParameters.extractPagination;
            val reversed = decodeUrlQueryValueOrSendError(
                reversedParameterName,
                Boolean.serializer()
            ) ?: return@get

            unianswer(
                PaginationResult.serializer(valueSerializer),
                originalRepo.values(parination, reversed)
            )
        }
    }

    get(keysRoute) {
        unifiedRouter.apply {
            val parination = call.request.queryParameters.extractPagination;
            val reversed = decodeUrlQueryValueOrSendError(
                reversedParameterName,
                Boolean.serializer()
            ) ?: return@get
            val value = decodeUrlQueryValue(valueParameterName, valueSerializer)

            unianswer(
                PaginationResult.serializer(keySerializer),
                value?.let { originalRepo.keys(value, parination, reversed) } ?: originalRepo.keys(parination, reversed)
            )
        }
    }

    get(containsRoute) {
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

    get(countRoute) {
        unifiedRouter.apply {
            unianswer(
                Long.serializer(),
                originalRepo.count()
            )
        }
    }
}

inline fun <K, V> Route.configureReadStandartKeyValueRepoRoutes (
    originalRepo: ReadStandardKeyValueRepo<K, V>,
    keySerializer: KSerializer<K>,
    valueSerializer: KSerializer<V>,
    valueNullableSerializer: KSerializer<V?>,
    serialFormat: StandardKtorSerialFormat = standardKtorSerialFormat,
    serialFormatContentType: ContentType = standardKtorSerialFormatContentType
) = configureReadStandartKeyValueRepoRoutes(
    originalRepo, keySerializer, valueSerializer, valueNullableSerializer, UnifiedRouter(serialFormat, serialFormatContentType)
)
