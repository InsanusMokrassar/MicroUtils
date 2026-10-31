package dev.inmo.micro_utils.common

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ByteArraySerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * A function type that allocates and returns a [ByteArray].
 */
typealias ByteArrayAllocator = () -> ByteArray

/**
 * A suspending function type that allocates and returns a [ByteArray].
 */
typealias SuspendByteArrayAllocator = suspend () -> ByteArray

/**
 * Converts this [ByteArray] to a [ByteArrayAllocator] that returns this array.
 */
val ByteArray.asAllocator: ByteArrayAllocator
    get() = { this }

/**
 * Converts this [ByteArray] to a [SuspendByteArrayAllocator] that returns this array.
 */
val ByteArray.asSuspendAllocator: SuspendByteArrayAllocator
    get() = { this }

/**
 * Converts this [ByteArrayAllocator] to a [SuspendByteArrayAllocator].
 */
val ByteArrayAllocator.asSuspendAllocator: SuspendByteArrayAllocator
    get() = { this() }

/**
 * Converts this [SuspendByteArrayAllocator] to a [ByteArrayAllocator] by invoking it and
 * wrapping the result in a non-suspending allocator.
 */
suspend fun SuspendByteArrayAllocator.asAllocator(): ByteArrayAllocator {
    return invoke().asAllocator
}

/**
 * Serializer for [ByteArrayAllocator]. Serializes the result of invoking the allocator.
 */
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

