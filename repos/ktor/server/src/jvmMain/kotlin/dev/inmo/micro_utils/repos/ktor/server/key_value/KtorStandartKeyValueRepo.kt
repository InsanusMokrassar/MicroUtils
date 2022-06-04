package dev.inmo.micro_utils.repos.ktor.server.key_value

import dev.inmo.micro_utils.ktor.common.StandardKtorSerialFormat
import dev.inmo.micro_utils.ktor.common.standardKtorSerialFormat
import dev.inmo.micro_utils.ktor.server.UnifiedRouter
import dev.inmo.micro_utils.ktor.server.standardKtorSerialFormatContentType
import dev.inmo.micro_utils.repos.KeyValueRepo
import io.ktor.http.ContentType
import io.ktor.server.routing.Route
import io.ktor.server.routing.route
import kotlinx.serialization.KSerializer

fun <K, V> Route.configureKeyValueRepoRoutes(
    baseSubpart: String,
    originalRepo: KeyValueRepo<K, V>,
    keySerializer: KSerializer<K>,
    valueSerializer: KSerializer<V>,
    valueNullableSerializer: KSerializer<V?>,
    unifiedRouter: UnifiedRouter
) {
    route(baseSubpart) {
        configureReadStandartKeyValueRepoRoutes(
            originalRepo,
            keySerializer,
            valueSerializer,
            valueNullableSerializer,
            unifiedRouter
        )
        configureWriteValueRepoRoutes(
            originalRepo,
            keySerializer,
            valueSerializer,
            unifiedRouter
        )
    }
}

fun <K, V> Route.configureStandartKeyValueRepoRoutes(
    baseSubpart: String,
    originalRepo: KeyValueRepo<K, V>,
    keySerializer: KSerializer<K>,
    valueSerializer: KSerializer<V>,
    valueNullableSerializer: KSerializer<V?>,
    serialFormat: StandardKtorSerialFormat = standardKtorSerialFormat,
    serialFormatContentType: ContentType = standardKtorSerialFormatContentType
) = configureKeyValueRepoRoutes(baseSubpart, originalRepo, keySerializer, valueSerializer, valueNullableSerializer, UnifiedRouter(serialFormat, serialFormatContentType))
