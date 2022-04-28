package dev.inmo.micro_utils.repos.ktor.server.one_to_many

import dev.inmo.micro_utils.ktor.common.StandardKtorSerialFormat
import dev.inmo.micro_utils.ktor.common.standardKtorSerialFormat
import dev.inmo.micro_utils.ktor.server.*
import dev.inmo.micro_utils.repos.WriteOneToManyKeyValueRepo
import dev.inmo.micro_utils.repos.ktor.common.one_to_many.*
import io.ktor.http.ContentType
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.*

fun <Key, Value> Route.configureOneToManyWriteKeyValueRepoRoutes(
    originalRepo: WriteOneToManyKeyValueRepo<Key, Value>,
    keySerializer: KSerializer<Key>,
    valueSerializer: KSerializer<Value>,
    unifiedRouter: UnifiedRouter
) {
    val keyValueSerializer = PairSerializer(keySerializer, valueSerializer)
    val keyValueMapSerializer = MapSerializer(keySerializer, ListSerializer(valueSerializer))

    unifiedRouter.apply {
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
    }

    post(addRoute) {
        unifiedRouter.apply {
            val obj = uniload(keyValueMapSerializer)

            unianswer(
                Unit.serializer(),
                originalRepo.add(obj)
            )
        }
    }

    post(removeRoute) {
        unifiedRouter.apply {
            val obj = uniload(keyValueMapSerializer)

            unianswer(
                Unit.serializer(),
                originalRepo.remove(obj),
            )
        }
    }

    post(clearRoute) {
        unifiedRouter.apply {
            val key = uniload(keySerializer)

            unianswer(
                Unit.serializer(),
                originalRepo.clear(key),
            )
        }
    }

    post(clearWithValueRoute) {
        unifiedRouter.apply {
            val v = uniload(valueSerializer)

            unianswer(
                Unit.serializer(),
                originalRepo.clearWithValue(v),
            )
        }
    }

    post(setRoute) {
        unifiedRouter.apply {
            val obj = uniload(keyValueMapSerializer)

            unianswer(
                Unit.serializer(),
                originalRepo.set(obj)
            )
        }
    }
}

fun <Key, Value> Route.configureOneToManyWriteKeyValueRepoRoutes(
    originalRepo: WriteOneToManyKeyValueRepo<Key, Value>,
    keySerializer: KSerializer<Key>,
    valueSerializer: KSerializer<Value>,
    serialFormat: StandardKtorSerialFormat = standardKtorSerialFormat,
    serialFormatContentType: ContentType = standardKtorSerialFormatContentType
) = configureOneToManyWriteKeyValueRepoRoutes(originalRepo, keySerializer, valueSerializer, UnifiedRouter(serialFormat, serialFormatContentType))
