package dev.inmo.micro_utils.serialization.base64

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

open class Base64Serializer<T>(
    private val converterFrom: (T) -> String,
    private val converterTo: (String) -> T,
) : KSerializer<T> {
    override val descriptor: SerialDescriptor = Base64BytesToFromStringSerializer.descriptor
    override fun deserialize(decoder: Decoder): T = converterTo(Base64BytesToFromStringSerializer.deserialize(decoder).decodeToString())
    override fun serialize(encoder: Encoder, value: T) = Base64BytesToFromStringSerializer.serialize(encoder, converterFrom(value).encodeToByteArray())
}

object Base64StringSerializer : Base64Serializer<String>({ it }, { it })
object Base64ByteArraySerializer : Base64Serializer<ByteArray>({ it.decodeToString() }, { it.encodeToByteArray() })
