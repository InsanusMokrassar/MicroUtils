package dev.inmo.micro_utils.repos.ktor.server.one_to_many

import dev.inmo.micro_utils.ktor.common.*
import dev.inmo.micro_utils.repos.*
import io.ktor.http.*
import io.ktor.server.routing.Route
import kotlinx.serialization.*

inline fun <reified Key : Any, reified Value : Any> Route.configureStandardKeyValuesRepoRoutes (
    originalRepo: OneToManyKeyValueRepo<Key, Value>,
    noinline idDeserializer: suspend (String) -> Key,
    noinline valueDeserializer: suspend (String) -> Value
) {
    configureReadStandardKeyValuesRepoRoutes(originalRepo, idDeserializer, valueDeserializer)
    configureWriteStandardKeyValuesRepoRoutes(originalRepo)
}

inline fun <reified Key : Any, reified Value : Any> Route.configureStandardKeyValuesRepoRoutes(
    originalRepo: OneToManyKeyValueRepo<Key, Value>,
    idsSerializer: DeserializationStrategy<Key>,
    valueSerializer: DeserializationStrategy<Value>,
    serialFormat: StringFormat
) = configureStandardKeyValuesRepoRoutes(
    originalRepo,
    {
        serialFormat.decodeFromString(idsSerializer, it.decodeURLQueryComponent())
    },
    {
        serialFormat.decodeFromString(valueSerializer, it.decodeURLQueryComponent())
    }
)

inline fun <reified Key : Any, reified Value : Any> Route.configureStandardKeyValuesRepoRoutes(
    originalRepo: OneToManyKeyValueRepo<Key, Value>,
    idsSerializer: DeserializationStrategy<Key>,
    valueSerializer: DeserializationStrategy<Value>,
    serialFormat: BinaryFormat
) = configureStandardKeyValuesRepoRoutes(
    originalRepo,
    {
        serialFormat.decodeHex(idsSerializer, it)
    },
    {
        serialFormat.decodeHex(valueSerializer, it)
    }
)

