package dev.inmo.micro_utils.serialization.base64

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * A serializer that encodes values to Base64 strings and decodes them back.
 * Uses custom conversion functions to transform between type [T] and string representation.
 *
 * @param T The type to serialize/deserialize
 * @param converterFrom Converts from type [T] to a string representation
 * @param converterTo Converts from a string representation back to type [T]
 */
open class Base64Serializer<T>(
    private val converterFrom: (T) -> String,
    private val converterTo: (String) -> T,
) : KSerializer<T> {
    override val descriptor: SerialDescriptor = Base64BytesToFromStringSerializer.descriptor
    override fun deserialize(decoder: Decoder): T = converterTo(Base64BytesToFromStringSerializer.deserialize(decoder).decodeToString())
    override fun serialize(encoder: Encoder, value: T) = Base64BytesToFromStringSerializer.serialize(encoder, converterFrom(value).encodeToByteArray())
}

/**
 * Serializer for [String] values encoded as Base64.
 */
object Base64StringSerializer : Base64Serializer<String>({ it }, { it })

/**
 * Serializer for [ByteArray] values encoded as Base64.
 */
object Base64ByteArraySerializer : Base64Serializer<ByteArray>({ it.decodeToString() }, { it.encodeToByteArray() })
