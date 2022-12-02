package dev.inmo.micro_utils.mime_types

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Suppress("OPT_IN_USAGE")
@Serializer(MimeType::class)
object MimeTypeSerializer : KSerializer<MimeType> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("mimeType", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): MimeType {
        val mimeType = decoder.decodeString()
        return mimeType(mimeType)
    }

    override fun serialize(encoder: Encoder, value: MimeType) {
        encoder.encodeString(value.raw)
    }
}
