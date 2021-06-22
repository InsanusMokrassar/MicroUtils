package dev.inmo.micro_utils.common

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ByteArraySerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

typealias ByteArrayAllocator = () -> ByteArray
typealias SuspendByteArrayAllocator = suspend () -> ByteArray

val ByteArray.asAllocator: ByteArrayAllocator
    get() = { this }
val ByteArray.asSuspendAllocator: SuspendByteArrayAllocator
    get() = { this }
val ByteArrayAllocator.asSuspendAllocator: SuspendByteArrayAllocator
    get() = { this() }
suspend fun SuspendByteArrayAllocator.asAllocator(): ByteArrayAllocator {
    return invoke().asAllocator
}

object ByteArrayAllocatorSerializer : KSerializer<ByteArrayAllocator> {
    private val realSerializer = ByteArraySerializer()
    override val descriptor: SerialDescriptor = realSerializer.descriptor

    override fun deserialize(decoder: Decoder): ByteArrayAllocator {
        val bytes = realSerializer.deserialize(decoder)
        return bytes.asAllocator
    }

    override fun serialize(encoder: Encoder, value: ByteArrayAllocator) {
        realSerializer.serialize(encoder, value())
    }
}

