package dev.inmo.micro_utils.ktor.common

import kotlinx.serialization.*
import kotlinx.serialization.cbor.Cbor
import kotlin.js.JsExport

typealias StandardKtorSerialFormat = BinaryFormat
typealias StandardKtorSerialInputData = ByteArray
@JsExport
val standardKtorSerialFormat: StandardKtorSerialFormat = Cbor {  }

@JsExport
inline fun <T> StandardKtorSerialFormat.decodeDefault(
    deserializationStrategy: DeserializationStrategy<T>,
    input: StandardKtorSerialInputData
): T = decodeFromByteArray(deserializationStrategy, input)

@JsExport
inline fun <T> StandardKtorSerialFormat.encodeDefault(
    serializationStrategy: SerializationStrategy<T>,
    data: T
): StandardKtorSerialInputData = encodeToByteArray(serializationStrategy, data)

@JsExport
@Deprecated("Will be removed in next major release due to useless")
val cbor = Cbor {}

@JsExport
inline fun <T> StandardKtorSerialFormat.decodeHex(
    deserializationStrategy: DeserializationStrategy<T>,
    input: String
): T = decodeFromHexString(deserializationStrategy, input)

@JsExport
inline fun <T> StandardKtorSerialFormat.encodeHex(
    serializationStrategy: SerializationStrategy<T>,
    data: T
): String = encodeToHexString(serializationStrategy, data)


