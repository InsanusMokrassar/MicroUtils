package dev.inmo.micro_utils.startup.launcher

import dev.inmo.kslog.common.i
import kotlinx.serialization.json.JsonObject
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.module

/**
 * Will create [KoinApplication], init, load modules using [StartLauncherPlugin] and start plugins using the same base
 * plugin
 *
 * @param rawConfig It is expected that this [JsonObject] will contain serialized [Config] ([StartLauncherPlugin] will
 * deserialize it in its [StartLauncherPlugin.setupDI]
 */
suspend fun start(rawConfig: JsonObject) {
    with(StartLauncherPlugin) {
        logger.i("Start initialization")
        val koinApp = KoinApplication.init()
        koinApp.modules(
            module {
                setupDI(rawConfig)
            }
        )
        logger.i("Modules loaded")
        startKoin(koinApp)
        logger.i("Koin started")
        startPlugin(koinApp.koin)
        logger.i("App has been setup")
    }
}
