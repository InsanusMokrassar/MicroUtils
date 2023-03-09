package dev.inmo.micro_utils.serialization.mapper

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Use this serializer when you have serializable type [I] and want to map it to some [O] in process of
 * serialization
 *
 * @param base Serializer for [I]
 * @param serialize Will be used in [serialize] method to convert incoming [O] to [I] and serialize with [base]
 */
open class MapperSerializationStrategy<I, O>(
    private val base: SerializationStrategy<I>,
    private val serialize: (O) -> I
) : SerializationStrategy<O> {
    override val descriptor: SerialDescriptor = base.descriptor

    override fun serialize(encoder: Encoder, value: O) {
        base.serialize(encoder, serialize(value))
    }
}
