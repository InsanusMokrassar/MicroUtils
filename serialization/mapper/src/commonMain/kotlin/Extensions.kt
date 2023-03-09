package dev.inmo.micro_utils.serialization.mapper

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.serializer
import kotlin.reflect.KClass

/**
 * Will create [MapperSerializationStrategy] to allow you to map [O] to [I] using [serialize] lambda during
 * serialization process
 */
inline fun <reified I : Any, O> SerializationStrategy<I>.mapSerialization(
    noinline serialize: (O) -> I
) = MapperSerializationStrategy(
    this,
    serialize
)

/**
 * Will create [MapperDeserializationStrategy] to allow you to map [I] to [O] using [deserialize] lambda during
 * deserialization process
 */
inline fun <reified I : Any, O> DeserializationStrategy<I>.mapDeserialization(
    noinline deserialize: (I) -> O
) = MapperDeserializationStrategy(
    this,
    deserialize
)

/**
 * Will create [MapperSerializer] to allow you to map [O] to [I] and vice verse using [serialize]/[deserialize] lambda during
 * serialization/deserialization process
 */
inline fun <reified I : Any, O> KSerializer<I>.mapFullSerialization(
    noinline serialize: (O) -> I,
    noinline deserialize: (I) -> O
) = MapperSerializer(
    this,
    serialize,
    deserialize
)

/**
 * Will create [MapperSerializationStrategy] to allow you to map [O] to [I] using [serialize] lambda during
 * serialization process
 */
@OptIn(InternalSerializationApi::class)
inline fun <reified I : Any, O> KClass<I>.mapSerialization(
    serializer: SerializationStrategy<I> = serializer(),
    noinline serialize: (O) -> I
) = serializer.mapSerialization(serialize)

/**
 * Will create [MapperDeserializationStrategy] to allow you to map [I] to [O] using [deserialize] lambda during
 * deserialization process
 */
@OptIn(InternalSerializationApi::class)
inline fun <reified I : Any, O> KClass<I>.mapDeserialization(
    serializer: DeserializationStrategy<I> = serializer(),
    noinline deserialize: (I) -> O
) = serializer.mapDeserialization(deserialize)

/**
 * Will create [MapperSerializer] to allow you to map [O] to [I] and vice verse using [serialize]/[deserialize] lambda during
 * serialization/deserialization process
 */
@OptIn(InternalSerializationApi::class)
inline fun <reified I : Any, O> KClass<I>.mapFullSerialization(
    serializer: KSerializer<I> = serializer(),
    noinline serialize: (O) -> I,
    noinline deserialize: (I) -> O
) = serializer.mapFullSerialization(serialize, deserialize)

/**
 * Will create [MapperSerializationStrategy] to allow you to map [O] to [I] using [serialize] lambda during
 * serialization process
 */
inline fun <reified I : Any, O> mappedSerializationStrategy(
    noinline serialize: (O) -> I,
) = serializer<I>().mapSerialization(serialize)

/**
 * Will create [MapperDeserializationStrategy] to allow you to map [I] to [O] using [deserialize] lambda during
 * deserialization process
 */
inline fun <reified I : Any, O> mappedDeserializationStrategy(
    noinline deserialize: (I) -> O
) = serializer<I>().mapDeserialization(deserialize)

/**
 * Will create [MapperSerializer] to allow you to map [O] to [I] and vice verse using [serialize]/[deserialize] lambda during
 * serialization/deserialization process
 */
inline fun <reified I : Any, O> mappedSerializer(
    noinline serialize: (O) -> I,
    noinline deserialize: (I) -> O
) = serializer<I>().mapFullSerialization(serialize, deserialize)
