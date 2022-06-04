package dev.inmo.micro_utils.repos.ktor.server.one_to_many

import dev.inmo.micro_utils.ktor.common.*
import dev.inmo.micro_utils.repos.*
import io.ktor.http.*
import io.ktor.server.routing.Route
import kotlinx.serialization.*

inline fun <reified Key : Any, reified Value : Any> Route.configureKeyValuesRepoRoutes (
    originalRepo: KeyValuesRepo<Key, Value>,
    noinline idDeserializer: suspend (String) -> Key,
    noinline valueDeserializer: suspend (String) -> Value
) {
    configureReadKeyValuesRepoRoutes(originalRepo, idDeserializer, valueDeserializer)
    configureWriteKeyValuesRepoRoutes(originalRepo)
}

inline fun <reified Key : Any, reified Value : Any> Route.configureKeyValuesRepoRoutes(
    originalRepo: KeyValuesRepo<Key, Value>,
    idsSerializer: DeserializationStrategy<Key>,
    valueSerializer: DeserializationStrategy<Value>,
    serialFormat: StringFormat
) = configureKeyValuesRepoRoutes(
    originalRepo,
    {
        serialFormat.decodeFromString(idsSerializer, it.decodeURLQueryComponent())
    },
    {
        serialFormat.decodeFromString(valueSerializer, it.decodeURLQueryComponent())
    }
)

inline fun <reified Key : Any, reified Value : Any> Route.configureKeyValuesRepoRoutes(
    originalRepo: KeyValuesRepo<Key, Value>,
    idsSerializer: DeserializationStrategy<Key>,
    valueSerializer: DeserializationStrategy<Value>,
    serialFormat: BinaryFormat
) = configureKeyValuesRepoRoutes(
    originalRepo,
    {
        serialFormat.decodeHex(idsSerializer, it)
    },
    {
        serialFormat.decodeHex(valueSerializer, it)
    }
)

