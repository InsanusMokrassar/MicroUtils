package dev.inmo.micro_utils.startup.launcher

import dev.inmo.kslog.common.KSLog
import dev.inmo.kslog.common.i
import kotlinx.serialization.json.jsonObject
import java.io.File

suspend fun main(args: Array<String>) {

    KSLog.default = KSLog("ServerLauncher")
    val (configPath) = args
    val file = File(configPath)
    KSLog.i("Start read config from ${file.absolutePath}")
    val json = defaultJson.parseToJsonElement(file.readText()).jsonObject
    KSLog.i("Config has been read")

    start(json)
}
