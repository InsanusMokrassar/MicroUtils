package dev.inmo.micro_utils.startup.launcher

import dev.inmo.kslog.common.KSLog
import dev.inmo.kslog.common.i
import dev.inmo.micro_utils.startup.plugin.ServerPlugin
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import org.koin.core.Koin
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.File

suspend fun main(args: Array<String>) {

    KSLog.default = KSLog("PlaguBot")
    val (configPath) = args
    val file = File(configPath)
    KSLog.i("Start read config from ${file.absolutePath}")
    val json = defaultJsonFormat.parseToJsonElement(file.readText()).jsonObject
    val config = defaultJsonFormat.decodeFromJsonElement(Config.serializer(), json)
    KSLog.i("Config has been read")

    PlaguBot(json, config).start().join()
}
