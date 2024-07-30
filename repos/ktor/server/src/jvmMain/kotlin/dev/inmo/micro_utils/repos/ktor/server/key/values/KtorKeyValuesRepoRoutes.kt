package dev.inmo.micro_utils.repos.ktor.server.key.values

import dev.inmo.micro_utils.ktor.common.*
import dev.inmo.micro_utils.repos.*
import io.ktor.http.*
import io.ktor.server.routing.Routing
import kotlinx.serialization.*

inline fun <reified Key : Any, reified Value : Any> Routing.configureKeyValuesRepoRoutes (
    originalRepo: KeyValuesRepo<Key, Value>,
    noinline keyDeserializer: suspend (String) -> Key,
    noinline valueDeserializer: suspend (String) -> Value
) {
    configureReadKeyValuesRepoRoutes(originalRepo, keyDeserializer, valueDeserializer)
    configureWriteKeyValuesRepoRoutes(originalRepo)
}

inline fun <reified Key : Any, reified Value : Any> Routing.configureKeyValuesRepoRoutes(
    originalRepo: KeyValuesRepo<Key, Value>,
    keySerializer: DeserializationStrategy<Key>,
    valueSerializer: DeserializationStrategy<Value>,
    serialFormat: StringFormat
) = configureKeyValuesRepoRoutes(
    originalRepo,
    {
        serialFormat.decodeFromString(keySerializer, it.decodeURLQueryComponent())
    },
    {
        serialFormat.decodeFromString(valueSerializer, it.decodeURLQueryComponent())
    }
)

inline fun <reified Key : Any, reified Value : Any> Routing.configureKeyValuesRepoRoutes(
    originalRepo: KeyValuesRepo<Key, Value>,
    keySerializer: DeserializationStrategy<Key>,
    valueSerializer: DeserializationStrategy<Value>,
    serialFormat: BinaryFormat
) = configureKeyValuesRepoRoutes(
    originalRepo,
    {
        serialFormat.decodeHex(keySerializer, it)
    },
    {
        serialFormat.decodeHex(valueSerializer, it)
    }
)

