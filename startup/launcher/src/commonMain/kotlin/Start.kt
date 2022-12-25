package dev.inmo.micro_utils.startup.launcher

import dev.inmo.micro_utils.startup.launcher.StartLauncherPlugin.setupDI
import kotlinx.serialization.json.JsonObject
import org.koin.core.KoinApplication

/**
 * Will create [KoinApplication], init, load modules using [StartLauncherPlugin] and start plugins using the same base
 * plugin
 *
 * @param rawConfig It is expected that this [JsonObject] will contain serialized [Config] ([StartLauncherPlugin] will
 * deserialize it in its [StartLauncherPlugin.setupDI]
 */
@Deprecated("Fully replaced with StartLauncherPlugin#start", ReplaceWith("StartLauncherPlugin.start(rawConfig)", "dev.inmo.micro_utils.startup.launcher.StartLauncherPlugin"))
suspend fun start(rawConfig: JsonObject) {
    StartLauncherPlugin.start(rawConfig)
}
