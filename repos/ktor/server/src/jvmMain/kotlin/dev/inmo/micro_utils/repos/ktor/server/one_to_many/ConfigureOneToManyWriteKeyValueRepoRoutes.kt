package dev.inmo.micro_utils.repos.ktor.server.one_to_many

import dev.inmo.micro_utils.ktor.server.*
import dev.inmo.micro_utils.repos.WriteOneToManyKeyValueRepo
import dev.inmo.micro_utils.repos.ktor.common.one_to_many.*
import io.ktor.application.call
import io.ktor.routing.Route
import io.ktor.routing.post
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.PairSerializer
import kotlinx.serialization.builtins.serializer

fun <Key, Value> Route.configureOneToManyWriteKeyValueRepoRoutes(
    originalRepo: WriteOneToManyKeyValueRepo<Key, Value>,
    keySerializer: KSerializer<Key>,
    valueSealizer: KSerializer<Value>,
) {
    val keyValueSerializer = PairSerializer(keySerializer, valueSealizer)

    includeWebsocketHandling(
        onNewValueRoute,
        originalRepo.onNewValue,
        keyValueSerializer
    )
    includeWebsocketHandling(
        onValueRemovedRoute,
        originalRepo.onValueRemoved,
        keyValueSerializer
    )
    includeWebsocketHandling(
        onDataClearedRoute,
        originalRepo.onDataCleared,
        keySerializer
    )

    post(addRoute) {
        val obj = call.uniload(
            keyValueSerializer
        )

        call.unianswer(
            Unit.serializer(),
            originalRepo.add(obj.first, obj.second)
        )
    }

    post(removeRoute) {
        val obj = call.uniload(
            keyValueSerializer
        )

        call.unianswer(
            Unit.serializer(),
            originalRepo.remove(obj.first, obj.second),
        )
    }

    post(clearRoute) {
        val key = call.uniload(keySerializer)

        call.unianswer(
            Unit.serializer(),
            originalRepo.clear(key),
        )
    }
}