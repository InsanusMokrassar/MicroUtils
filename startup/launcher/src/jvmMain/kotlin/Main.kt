package dev.inmo.micro_utils.startup.launcher

import dev.inmo.kslog.common.KSLog
import dev.inmo.kslog.common.i
import kotlinx.serialization.json.jsonObject
import java.io.File

/**
 * It is expected, that [args] will contain ONE argument with path to the config json. Sample of launching:
 *
 * ```bash
 * ./gradlew run --args="sample.config.json"
 * ```
 *
 * Content of `sample.config.json` described in [Config] KDocs.
 *
 * You may build runnable app using:
 *
 * ```bash
 * ./gradlew assembleDist
 * ```
 *
 * In that case in `build/distributions` folder you will be able to find zip and tar files with all required
 * tools for application running (via their `bin/app_name` binary). In that case yoy will not need to pass
 * `--args=...` and launch will look like `./bin/app_name sample.config.json`
 */
suspend fun main(args: Array<String>) {

    KSLog.default = KSLog("Launcher")
    val (configPath) = args
    val file = File(configPath)
    KSLog.i("Start read config from ${file.absolutePath}")
    val json = defaultJson.parseToJsonElement(file.readText()).jsonObject
    KSLog.i("Config has been read")

    StartLauncherPlugin.start(json)
}
