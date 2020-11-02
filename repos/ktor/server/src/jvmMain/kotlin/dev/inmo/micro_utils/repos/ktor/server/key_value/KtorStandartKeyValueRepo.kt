package dev.inmo.micro_utils.repos.ktor.server.key_value

import dev.inmo.micro_utils.repos.StandardKeyValueRepo
import io.ktor.routing.Route
import io.ktor.routing.route
import kotlinx.serialization.KSerializer

fun <K, V> Route.configureStandartKeyValueRepoRoutes(
    baseSubpart: String,
    originalRepo: StandardKeyValueRepo<K, V>,
    keySerializer: KSerializer<K>,
    valueSerializer: KSerializer<V>,
    valueNullableSerializer: KSerializer<V?>,
) {
    route(baseSubpart) {
        configureReadStandartKeyValueRepoRoutes(
            originalRepo,
            keySerializer,
            valueSerializer,
            valueNullableSerializer,
        )
        configureWriteStandartKeyValueRepoRoutes(
            originalRepo,
            keySerializer,
            valueSerializer,
        )
    }
}