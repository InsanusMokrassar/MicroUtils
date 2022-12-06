package dev.inmo.micro_utils.startup.plugin

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

actual object ServerPluginSerializer : KSerializer<ServerPlugin> {
    override val descriptor: SerialDescriptor
    get() = String.serializer().descriptor

    override fun deserialize(decoder: Decoder): ServerPlugin {
        val kclass = Class.forName(decoder.decodeString()).kotlin
        return (kclass.objectInstance ?: kclass.constructors.first { it.parameters.isEmpty() }.call()) as ServerPlugin
    }

    override fun serialize(encoder: Encoder, value: ServerPlugin) {
        encoder.encodeString(
            value::class.java.canonicalName
        )
    }
}
