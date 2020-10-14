package dev.inmo.micro_utils.common

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ByteArraySerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.js.JsExport

typealias ByteArrayAllocator = () -> ByteArray

@JsExport
val ByteArray.asAllocator: ByteArrayAllocator
    get() = { this }

@JsExport
object ByteArrayAllocatorSerializer : KSerializer<ByteArrayAllocator> {
    private val realSerializer = ByteArraySerializer()
    override val descriptor: SerialDescriptor = realSerializer.descriptor

    override fun deserialize(decoder: Decoder): ByteArrayAllocator {
        val bytes = realSerializer.deserialize(decoder)
        return { bytes }
    }

    override fun serialize(encoder: Encoder, value: ByteArrayAllocator) {
        realSerializer.serialize(encoder, value())
    }
}

