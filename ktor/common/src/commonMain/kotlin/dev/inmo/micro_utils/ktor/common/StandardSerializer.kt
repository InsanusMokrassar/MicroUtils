@file:Suppress("NOTHING_TO_INLINE")

package dev.inmo.micro_utils.ktor.common

import kotlinx.serialization.*
import kotlinx.serialization.cbor.Cbor

/**
 * Type alias for the standard serialization format used in Ktor utilities, which is [BinaryFormat].
 */
typealias StandardKtorSerialFormat = BinaryFormat

/**
 * Type alias for the standard serialization input data type, which is [ByteArray].
 */
typealias StandardKtorSerialInputData = ByteArray

/**
 * The standard Ktor serialization format instance, configured as CBOR.
 */
val standardKtorSerialFormat: StandardKtorSerialFormat = Cbor {  }

/**
 * Decodes data from [StandardKtorSerialInputData] using the standard format.
 *
 * @param T The type to decode to
 * @param deserializationStrategy The deserialization strategy for type [T]
 * @param input The byte array input data to decode
 * @return The decoded value of type [T]
 */
inline fun <T> StandardKtorSerialFormat.decodeDefault(
    deserializationStrategy: DeserializationStrategy<T>,
    input: StandardKtorSerialInputData
): T = decodeFromByteArray(deserializationStrategy, input)

/**
 * Encodes data to [StandardKtorSerialInputData] using the standard format.
 *
 * @param T The type to encode
 * @param serializationStrategy The serialization strategy for type [T]
 * @param data The data to encode
 * @return The encoded byte array
 */
inline fun <T> StandardKtorSerialFormat.encodeDefault(
    serializationStrategy: SerializationStrategy<T>,
    data: T
): StandardKtorSerialInputData = encodeToByteArray(serializationStrategy, data)

/**
 * A CBOR instance for serialization operations.
 */
val cbor = Cbor {}

/**
 * Decodes data from a hex string using the standard binary format.
 *
 * @param T The type to decode to
 * @param deserializationStrategy The deserialization strategy for type [T]
 * @param input The hex string to decode
 * @return The decoded value of type [T]
 */
inline fun <T> StandardKtorSerialFormat.decodeHex(
    deserializationStrategy: DeserializationStrategy<T>,
    input: String
): T = decodeFromHexString(deserializationStrategy, input)

/**
 * Encodes data to a hex string using the standard binary format.
 *
 * @param T The type to encode
 * @param serializationStrategy The serialization strategy for type [T]
 * @param data The data to encode
 * @return The encoded hex string
 */
inline fun <T> StandardKtorSerialFormat.encodeHex(
    serializationStrategy: SerializationStrategy<T>,
    data: T
): String = encodeToHexString(serializationStrategy, data)


