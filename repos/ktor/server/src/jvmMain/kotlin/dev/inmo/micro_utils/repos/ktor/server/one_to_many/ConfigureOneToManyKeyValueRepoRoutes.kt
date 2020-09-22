package dev.inmo.micro_utils.repos.ktor.server.one_to_many

import dev.inmo.micro_utils.repos.OneToManyKeyValueRepo
import io.ktor.routing.Route
import io.ktor.routing.route
import kotlinx.serialization.KSerializer

fun <Key, Value> Route.configureOneToManyKeyValueRepoRoutes(
    baseSubpart: String,
    originalRepo: OneToManyKeyValueRepo<Key, Value>,
    keySerializer: KSerializer<Key>,
    valueSealizer: KSerializer<Value>,
) {
    route(baseSubpart) {
        configureOneToManyReadKeyValueRepoRoutes(originalRepo, keySerializer, valueSealizer)
        configureOneToManyWriteKeyValueRepoRoutes(originalRepo, keySerializer, valueSealizer)
    }
}