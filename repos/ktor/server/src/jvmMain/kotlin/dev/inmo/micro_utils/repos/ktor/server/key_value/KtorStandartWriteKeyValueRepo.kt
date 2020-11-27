package dev.inmo.micro_utils.repos.ktor.server.key_value

import dev.inmo.micro_utils.ktor.common.StandardKtorSerialFormat
import dev.inmo.micro_utils.ktor.common.standardKtorSerialFormat
import dev.inmo.micro_utils.ktor.server.*
import dev.inmo.micro_utils.repos.WriteStandardKeyValueRepo
import dev.inmo.micro_utils.repos.ktor.common.key_value.*
import io.ktor.http.ContentType
import io.ktor.routing.Route
import io.ktor.routing.post
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.*

fun <K, V> Route.configureWriteStandardKeyValueRepoRoutes (
    originalRepo: WriteStandardKeyValueRepo<K, V>,
    keySerializer: KSerializer<K>,
    valueSerializer: KSerializer<V>,
    unifiedRouter: UnifiedRouter
) {
    val keyValueMapSerializer = MapSerializer(keySerializer, valueSerializer)
    val keysListSerializer = ListSerializer(keySerializer)
    unifiedRouter.apply {
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
    }

    post(setRoute) {
        unifiedRouter.apply {
            val toSet = uniload(
                keyValueMapSerializer
            )

            unianswer(Unit.serializer(), originalRepo.set(toSet))
        }
    }

    post(unsetRoute) {
        unifiedRouter.apply {
            val toUnset = uniload(keysListSerializer)

            unianswer(Unit.serializer(), originalRepo.unset(toUnset))
        }
    }
}

fun <K, V> Route.configureWriteStandartKeyValueRepoRoutes (
    originalRepo: WriteStandardKeyValueRepo<K, V>,
    keySerializer: KSerializer<K>,
    valueSerializer: KSerializer<V>,
    serialFormat: StandardKtorSerialFormat = standardKtorSerialFormat,
    serialFormatContentType: ContentType = standardKtorSerialFormatContentType
) = configureWriteStandardKeyValueRepoRoutes(originalRepo, keySerializer, valueSerializer, UnifiedRouter(serialFormat, serialFormatContentType))
