package dev.inmo.micro_utils.serialization.base64

import dev.inmo.micro_utils.crypto.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Converts [ByteArray] into base64 encoded string due to serialization and decode base64 encoded string into bytes
 */
object Base64BytesToFromStringSerializer: KSerializer<ByteArray> {
    override val descriptor: SerialDescriptor = String.serializer().descriptor
    override fun deserialize(decoder: Decoder): SourceBytes = decoder.decodeString().decodeBase64()
    override fun serialize(encoder: Encoder, value: SourceBytes) = encoder.encodeString(value.encodeBase64String())
}