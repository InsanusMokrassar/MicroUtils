package dev.inmo.micro_utils.startup.launcher

import dev.inmo.kslog.common.KSLog
import dev.inmo.kslog.common.i
import dev.inmo.micro_utils.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.jsonObject
import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext
import org.koin.dsl.module
import java.io.File

suspend fun main(args: Array<String>) {

    KSLog.default = KSLog("ServerLauncher")
    val (configPath) = args
    val file = File(configPath)
    KSLog.i("Start read config from ${file.absolutePath}")
    val json = defaultJson.parseToJsonElement(file.readText()).jsonObject
    KSLog.i("Config has been read")

    with(StartupLauncher) {
        logger.i("Start initialization")
        val koinApp = KoinApplication.init()
        koinApp.modules(
            module {
                setupDI(json)
            }
        )
        logger.i("Modules loaded")
        GlobalContext.startKoin(koinApp)
        logger.i("Koin started")
        startPlugin(koinApp.koin)
        logger.i("Behaviour builder has been setup")
    }
}
