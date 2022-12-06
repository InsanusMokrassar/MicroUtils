package dev.inmo.micro_utils.startup.plugin

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

actual object StartPluginSerializer : KSerializer<StartPlugin> {
    override val descriptor: SerialDescriptor
    get() = String.serializer().descriptor

    override fun deserialize(decoder: Decoder): StartPlugin {
        val kclass = Class.forName(decoder.decodeString()).kotlin
        return (kclass.objectInstance ?: kclass.constructors.first { it.parameters.isEmpty() }.call()) as StartPlugin
    }

    override fun serialize(encoder: Encoder, value: StartPlugin) {
        encoder.encodeString(
            value::class.java.canonicalName
        )
    }
}
