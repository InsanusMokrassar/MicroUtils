package dev.inmo.micro_utils.repos.ktor.server.key.value

import dev.inmo.micro_utils.ktor.common.*
import dev.inmo.micro_utils.repos.KeyValueRepo
import io.ktor.http.*
import io.ktor.server.routing.Route
import kotlinx.serialization.*

inline fun <reified Key : Any, reified Value : Any> Route.configureKeyValueRepoRoutes (
    originalRepo: KeyValueRepo<Key, Value>,
    noinline idDeserializer: suspend (String) -> Key,
    noinline valueDeserializer: suspend (String) -> Value
) {
    configureReadKeyValueRepoRoutes(originalRepo, idDeserializer, valueDeserializer)
    configureWriteKeyValueRepoRoutes(originalRepo)
}

inline fun <reified Key : Any, reified Value : Any> Route.configureKeyValueRepoRoutes(
    originalRepo: KeyValueRepo<Key, Value>,
    idsSerializer: DeserializationStrategy<Key>,
    valueSerializer: DeserializationStrategy<Value>,
    serialFormat: StringFormat
) = configureKeyValueRepoRoutes(
    originalRepo,
    {
        serialFormat.decodeFromString(idsSerializer, it.decodeURLQueryComponent())
    },
    {
        serialFormat.decodeFromString(valueSerializer, it.decodeURLQueryComponent())
    }
)

inline fun <reified Key : Any, reified Value : Any> Route.configureKeyValueRepoRoutes(
    originalRepo: KeyValueRepo<Key, Value>,
    idsSerializer: DeserializationStrategy<Key>,
    valueSerializer: DeserializationStrategy<Value>,
    serialFormat: BinaryFormat
) = configureKeyValueRepoRoutes(
    originalRepo,
    {
        serialFormat.decodeHex(idsSerializer, it)
    },
    {
        serialFormat.decodeHex(valueSerializer, it)
    }
)

