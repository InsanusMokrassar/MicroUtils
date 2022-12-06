package dev.inmo.micro_utils.startup.launcher

import dev.inmo.kslog.common.i
import kotlinx.serialization.json.JsonObject
import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext
import org.koin.dsl.module

suspend fun start(rawConfig: JsonObject) {
    with(StartupLauncherPlugin) {
        logger.i("Start initialization")
        val koinApp = KoinApplication.init()
        koinApp.modules(
            module {
                setupDI(rawConfig)
            }
        )
        logger.i("Modules loaded")
        GlobalContext.startKoin(koinApp)
        logger.i("Koin started")
        startPlugin(koinApp.koin)
        logger.i("App has been setup")
    }
}
