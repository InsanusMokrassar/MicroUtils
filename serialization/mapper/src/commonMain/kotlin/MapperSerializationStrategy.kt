package dev.inmo.micro_utils.serialization.mapper

import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Encoder

/**
 * Use this serializer when you have serializable type [I] and want to map it to some [O] in process of
 * serialization
 *
 * @param base Serializer for [I]
 * @param internalSerialize Will be used in [internalSerialize] method to convert incoming [O] to [I] and serialize with [base]
 */
open class MapperSerializationStrategy<I, O>(
    private val base: SerializationStrategy<I>,
    private val internalSerialize: (Encoder, O) -> I
) : SerializationStrategy<O> {
    override val descriptor: SerialDescriptor = base.descriptor

    constructor(
        base: SerializationStrategy<I>,
        serialize: (O) -> I
    ) : this(base, { _, o -> serialize(o) })

    override fun serialize(encoder: Encoder, value: O) {
        base.serialize(encoder, internalSerialize(encoder, value))
    }
}
