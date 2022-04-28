package dev.inmo.micro_utils.repos.ktor.server.one_to_many

import dev.inmo.micro_utils.ktor.common.StandardKtorSerialFormat
import dev.inmo.micro_utils.ktor.common.standardKtorSerialFormat
import dev.inmo.micro_utils.ktor.server.UnifiedRouter
import dev.inmo.micro_utils.ktor.server.standardKtorSerialFormatContentType
import dev.inmo.micro_utils.repos.OneToManyKeyValueRepo
import io.ktor.http.ContentType
import io.ktor.server.routing.Route
import io.ktor.server.routing.route
import kotlinx.serialization.KSerializer

fun <Key, Value> Route.configureOneToManyKeyValueRepoRoutes(
    baseSubpart: String,
    originalRepo: OneToManyKeyValueRepo<Key, Value>,
    keySerializer: KSerializer<Key>,
    valueSerializer: KSerializer<Value>,
    unifiedRouter: UnifiedRouter
) {
    route(baseSubpart) {
        configureOneToManyReadKeyValueRepoRoutes(originalRepo, keySerializer, valueSerializer, unifiedRouter)
        configureOneToManyWriteKeyValueRepoRoutes(originalRepo, keySerializer, valueSerializer, unifiedRouter)
    }
}

fun <Key, Value> Route.configureOneToManyKeyValueRepoRoutes(
    baseSubpart: String,
    originalRepo: OneToManyKeyValueRepo<Key, Value>,
    keySerializer: KSerializer<Key>,
    valueSerializer: KSerializer<Value>,
    serialFormat: StandardKtorSerialFormat = standardKtorSerialFormat,
    serialFormatContentType: ContentType = standardKtorSerialFormatContentType
) = configureOneToManyKeyValueRepoRoutes(
    baseSubpart, originalRepo, keySerializer, valueSerializer, UnifiedRouter(serialFormat, serialFormatContentType)
)
