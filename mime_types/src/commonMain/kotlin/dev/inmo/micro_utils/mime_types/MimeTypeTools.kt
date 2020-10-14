package dev.inmo.micro_utils.mime_types

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.js.JsExport

private val mimesCache = mutableMapOf<String, MimeType>().also {
    knownMimeTypes.forEach { mimeType -> it[mimeType.raw] = mimeType }
}

@JsExport
fun mimeType(raw: String) = mimesCache.getOrPut(raw) {
    parseMimeType(raw)
}

@JsExport
internal fun parseMimeType(raw: String): MimeType = CustomMimeType(raw)

@Serializer(MimeType::class)
@JsExport
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
