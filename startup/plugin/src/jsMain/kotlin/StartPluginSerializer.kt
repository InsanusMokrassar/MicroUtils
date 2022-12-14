package dev.inmo.micro_utils.startup.plugin

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

actual object StartPluginSerializer : KSerializer<StartPlugin> {
    override val descriptor: SerialDescriptor
        get() = TODO("Not yet implemented")

    override fun deserialize(decoder: Decoder): StartPlugin {
        TODO("Not yet implemented")
    }

    override fun serialize(encoder: Encoder, value: StartPlugin) {
        TODO("Not yet implemented")
    }

}
