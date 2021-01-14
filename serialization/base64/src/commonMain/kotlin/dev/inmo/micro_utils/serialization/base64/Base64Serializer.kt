package dev.inmo.micro_utils.serialization.base64

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder

class Base64Serializer<T>(private val typeSerializer: KSerializer<T>) : KSerializer<T> {
    override val descriptor: SerialDescriptor = String.serializer().descriptor
    override fun deserialize(decoder: Decoder): T {
        TODO("Not yet implemented")
    }
}
