package dev.inmo.micro_utils.startup.launcher

import dev.inmo.kslog.common.KSLog
import dev.inmo.kslog.common.i
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject

@Deprecated("Useless due to including of the same functionality in StrtLauncherPlugin")
object PluginsStarter {
    init {
        KSLog.default = KSLog("Launcher")
    }

    /**
     * It is expected that you have registered all the [dev.inmo.micro_utils.startup.plugin.StartPlugin]s of your JS
     * app inside of [dev.inmo.micro_utils.startup.plugin.StartPluginSerializer] using its
     * [dev.inmo.micro_utils.startup.plugin.StartPluginSerializer.registerPlugin] method
     */
    suspend fun startPlugins(json: JsonObject) = StartLauncherPlugin.start(json)
    /**
     * Will convert [config] to [JsonObject] with auto registration of [dev.inmo.micro_utils.startup.plugin.StartPlugin]s
     * in [dev.inmo.micro_utils.startup.plugin.StartPluginSerializer]
     */
    suspend fun startPlugins(config: Config) = StartLauncherPlugin.start(config)
}
