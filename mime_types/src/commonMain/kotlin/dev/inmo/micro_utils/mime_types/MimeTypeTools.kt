package dev.inmo.micro_utils.mime_types

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

private val mimesCache = mutableMapOf<String, MimeType>().also {
    knownMimeTypes.forEach { mimeType -> it[mimeType.raw] = mimeType }
}

fun mimeType(raw: String) = mimesCache.getOrPut(raw) {
    parseMimeType(raw)
}

internal fun parseMimeType(raw: String): MimeType = CustomMimeType(raw)

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
