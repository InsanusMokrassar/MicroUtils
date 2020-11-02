package dev.inmo.micro_utils.repos.ktor.server.one_to_many

import dev.inmo.micro_utils.ktor.server.*
import dev.inmo.micro_utils.pagination.PaginationResult
import dev.inmo.micro_utils.pagination.extractPagination
import dev.inmo.micro_utils.repos.ReadOneToManyKeyValueRepo
import dev.inmo.micro_utils.repos.ktor.common.one_to_many.*
import io.ktor.application.call
import io.ktor.routing.Route
import io.ktor.routing.get
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer

fun <Key, Value> Route.configureOneToManyReadKeyValueRepoRoutes(
    originalRepo: ReadOneToManyKeyValueRepo<Key, Value>,
    keySerializer: KSerializer<Key>,
    valueSealizer: KSerializer<Value>,
) {
    val paginationKeyResult = PaginationResult.serializer(keySerializer)
    val paginationValueResult = PaginationResult.serializer(valueSealizer)

    get(getRoute) {
        val pagination = call.request.queryParameters.extractPagination
        val key = call.decodeUrlQueryValueOrSendError(
            keyParameterName,
            keySerializer
        ) ?: return@get
        val reversed = call.decodeUrlQueryValue(
            reversedParameterName,
            Boolean.serializer()
        ) ?: false

        call.unianswer(
            paginationValueResult,
            originalRepo.get(key, pagination, reversed)
        )
    }

    get(keysRoute) {
        val pagination = call.request.queryParameters.extractPagination
        val reversed = call.decodeUrlQueryValue(
            reversedParameterName,
            Boolean.serializer()
        ) ?: false

        call.unianswer(
            paginationKeyResult,
            originalRepo.keys(pagination, reversed)
        )
    }

    get(containsByKeyRoute) {
        val key = call.decodeUrlQueryValueOrSendError(
            keyParameterName,
            keySerializer
        ) ?: return@get

        call.unianswer(
            Boolean.serializer(),
            originalRepo.contains(key)
        )
    }

    get(containsByKeyValueRoute) {
        val key = call.decodeUrlQueryValueOrSendError(
            keyParameterName,
            keySerializer
        ) ?: return@get
        val value = call.decodeUrlQueryValueOrSendError(
            valueParameterName,
            valueSealizer
        ) ?: return@get

        call.unianswer(
            Boolean.serializer(),
            originalRepo.contains(key, value)
        )
    }

    get(countByKeyRoute) {
        val key = call.decodeUrlQueryValueOrSendError(
            keyParameterName,
            keySerializer
        ) ?: return@get

        call.unianswer(
            Long.serializer(),
            originalRepo.count(key)
        )
    }

    get(countRoute) {
        call.unianswer(
            Long.serializer(),
            originalRepo.count()
        )
    }
}