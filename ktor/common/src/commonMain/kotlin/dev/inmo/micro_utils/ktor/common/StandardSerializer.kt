@file:Suppress("NOTHING_TO_INLINE")

package dev.inmo.micro_utils.ktor.common

import kotlinx.serialization.*
import kotlinx.serialization.cbor.Cbor

typealias StandardKtorSerialFormat = BinaryFormat
typealias StandardKtorSerialInputData = ByteArray
val standardKtorSerialFormat: StandardKtorSerialFormat = Cbor {  }

inline fun <T> StandardKtorSerialFormat.decodeDefault(
    deserializationStrategy: DeserializationStrategy<T>,
    input: StandardKtorSerialInputData
): T = decodeFromByteArray(deserializationStrategy, input)

inline fun <T> StandardKtorSerialFormat.encodeDefault(
    serializationStrategy: SerializationStrategy<T>,
    data: T
): StandardKtorSerialInputData = encodeToByteArray(serializationStrategy, data)

val cbor = Cbor {}

inline fun <T> StandardKtorSerialFormat.decodeHex(
    deserializationStrategy: DeserializationStrategy<T>,
    input: String
): T = decodeFromHexString(deserializationStrategy, input)

inline fun <T> StandardKtorSerialFormat.encodeHex(
    serializationStrategy: SerializationStrategy<T>,
    data: T
): String = encodeToHexString(serializationStrategy, data)


