package dev.inmo.micro_utils.serialization.base64

import dev.inmo.micro_utils.common.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

open class Base64Serializer<T>(
    private val converterFrom: (T) -> String,
    private val converterTo: (String) -> T,
) : KSerializer<T> {
    override val descriptor: SerialDescriptor = String.serializer().descriptor
    override fun deserialize(decoder: Decoder): T = converterTo(decoder.decodeString().decodeBase64String())
    override fun serialize(encoder: Encoder, value: T) = encoder.encodeString(converterFrom(value).encodeBase64String())
}

object Base64StringSerializer : Base64Serializer<String>({ it }, { it })
object Base64ByteArraySerializer : Base64Serializer<ByteArray>({ it.decodeToString() }, { it.encodeToByteArray() })
