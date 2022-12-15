package dev.inmo.micro_utils.startup.plugin

import com.benasher44.uuid.uuid4
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

actual object StartPluginSerializer : KSerializer<StartPlugin> {
    private val registeredPlugins = mutableMapOf<String, StartPlugin>()
    private val registeredPluginsByPlugin = mutableMapOf<StartPlugin, String>()
    override val descriptor: SerialDescriptor = String.serializer().descriptor

    override fun deserialize(decoder: Decoder): StartPlugin {
        val name = decoder.decodeString()
        return registeredPlugins[name] ?: error("Unable to find startup plugin for $name")
    }

    override fun serialize(encoder: Encoder, value: StartPlugin) {
        val name = registeredPluginsByPlugin[value] ?: uuid4().toString().also {
            registeredPlugins[it] = value
            registeredPluginsByPlugin[value] = it
        }
        encoder.encodeString(name)
    }

    /**
     * Register plugin inside of this [KSerializer]. Since plugin has been registered, you may use its [name] in any
     * serialized [dev.inmo.micro_utils.startup.launcher.Config] to retrieve [plugin] you passed here
     */
    fun registerPlugin(name: String, plugin: StartPlugin) {
        registeredPlugins[name] = plugin
    }
}
