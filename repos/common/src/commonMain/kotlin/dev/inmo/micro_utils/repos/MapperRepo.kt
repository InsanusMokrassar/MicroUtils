package dev.inmo.micro_utils.repos

import dev.inmo.micro_utils.common.*

/**
 * Interface for repositories that provide bidirectional mapping between two sets of key-value types.
 * This is useful for adapting repositories to work with different key and value types.
 *
 * @param FromKey The original key type (inner/source)
 * @param FromValue The original value type (inner/source)
 * @param ToKey The target key type (outer/destination)
 * @param ToValue The target value type (outer/destination)
 */
@Suppress("UNCHECKED_CAST")
interface MapperRepo<FromKey, FromValue, ToKey, ToValue> {
    /**
     * Provides a mapper for converting keys between inner and outer types.
     */
    val keyMapper: SimpleSuspendableMapper<FromKey, ToKey>
        get() = simpleSuspendableMapper(
            { it.toInnerKey() },
            { it.toOutKey() }
        )
    
    /**
     * Provides a mapper for converting values between inner and outer types.
     */
    val valueMapper: SimpleSuspendableMapper<FromValue, ToValue>
        get() = simpleSuspendableMapper(
            { it.toInnerValue() },
            { it.toOutValue() }
        )

    /**
     * Converts a key from the inner type to the outer type.
     */
    suspend fun FromKey.toOutKey() = this as ToKey
    
    /**
     * Converts a value from the inner type to the outer type.
     */
    suspend fun FromValue.toOutValue() = this as ToValue

    /**
     * Converts a key from the outer type to the inner type.
     */
    suspend fun ToKey.toInnerKey() = this as FromKey
    
    /**
     * Converts a value from the outer type to the inner type.
     */
    suspend fun ToValue.toInnerValue() = this as FromValue

    companion object
}

/**
 * Simple implementation of [MapperRepo] that uses provided conversion functions.
 *
 * @param FromKey The original key type
 * @param FromValue The original value type
 * @param ToKey The target key type
 * @param ToValue The target value type
 */
class SimpleMapperRepo<FromKey, FromValue, ToKey, ToValue>(
    private val keyFromToTo: suspend FromKey.() -> ToKey,
    private val valueFromToTo: suspend FromValue.() -> ToValue,
    private val keyToToFrom: suspend ToKey.() -> FromKey,
    private val valueToToFrom: suspend ToValue.() -> FromValue
) : MapperRepo<FromKey, FromValue, ToKey, ToValue> {
    override suspend fun FromKey.toOutKey(): ToKey = keyFromToTo()
    override suspend fun FromValue.toOutValue(): ToValue = valueFromToTo()
    override suspend fun ToKey.toInnerKey(): FromKey = keyToToFrom()
    override suspend fun ToValue.toInnerValue(): FromValue = valueToToFrom()
}

/**
 * Factory function for creating a [SimpleMapperRepo] with custom conversion functions.
 */
operator fun <FromKey, FromValue, ToKey, ToValue> MapperRepo.Companion.invoke(
    keyFromToTo: suspend FromKey.() -> ToKey,
    valueFromToTo: suspend FromValue.() -> ToValue,
    keyToToFrom: suspend ToKey.() -> FromKey,
    valueToToFrom: suspend ToValue.() -> FromValue
) = SimpleMapperRepo(keyFromToTo, valueFromToTo, keyToToFrom, valueToToFrom)

/**
 * Creates a [MapperRepo] with optional custom conversion functions.
 * By default, uses casting for type conversions.
 *
 * @param keyFromToTo Function to convert keys from inner to outer type
 * @param valueFromToTo Function to convert values from inner to outer type
 * @param keyToToFrom Function to convert keys from outer to inner type
 * @param valueToToFrom Function to convert values from outer to inner type
 */
inline fun <reified FromKey, reified FromValue, reified ToKey, reified ToValue> mapper(
    noinline keyFromToTo: suspend FromKey.() -> ToKey = { this as ToKey },
    noinline valueFromToTo: suspend FromValue.() -> ToValue = { this as ToValue },
    noinline keyToToFrom: suspend ToKey.() -> FromKey = { this as FromKey },
    noinline valueToToFrom: suspend ToValue.() -> FromValue = { this as FromValue },
) = MapperRepo(keyFromToTo, valueFromToTo, keyToToFrom, valueToToFrom)
