package dev.inmo.micro_utils.startup.plugin

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonObject
import org.koin.core.Koin
import org.koin.core.module.Module

@Serializable(ServerPlugin.Companion::class)
interface ServerPlugin {
    fun Module.setupDI(config: JsonObject) {}

    suspend fun startPlugin(koin: Koin) {}

    companion object : KSerializer<ServerPlugin> {
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
}
