package dev.inmo.micro_utils.repos.ktor.server.key_value

import dev.inmo.micro_utils.ktor.server.*
import dev.inmo.micro_utils.repos.WriteStandardKeyValueRepo
import dev.inmo.micro_utils.repos.ktor.common.key_value.*
import io.ktor.application.call
import io.ktor.routing.Route
import io.ktor.routing.post
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.*

fun <K, V> Route.configureWriteStandartKeyValueRepoRoutes (
    originalRepo: WriteStandardKeyValueRepo<K, V>,
    keySerializer: KSerializer<K>,
    valueSerializer: KSerializer<V>,
) {
    val keyValueMapSerializer = MapSerializer(keySerializer, valueSerializer)
    val keysListSerializer = ListSerializer(keySerializer)
    includeWebsocketHandling(
        onNewValueRoute,
        originalRepo.onNewValue,
        PairSerializer(keySerializer, valueSerializer)
    )

    includeWebsocketHandling(
        onValueRemovedRoute,
        originalRepo.onValueRemoved,
        keySerializer
    )

    post(setRoute) {
        val toSet = call.uniload(
            keyValueMapSerializer
        )

        call.unianswer(Unit.serializer(), originalRepo.set(toSet))
    }

    post(unsetRoute) {
        val toUnset = call.uniload(keysListSerializer)

        call.unianswer(Unit.serializer(), originalRepo.unset(toUnset))
    }
}