package dev.inmo.micro_utils.startup.launcher

import dev.inmo.micro_utils.startup.plugin.ServerPlugin
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import org.koin.core.Koin
import org.koin.core.module.Module
import org.koin.dsl.module

class ServerLauncher : ServerPlugin {
    val defaultJson = Json {
        ignoreUnknownKeys = true
    }

    override fun Module.setupDI(config: JsonObject) {
        val pluginsConfig = defaultJson.decodeFromJsonElement(Config.serializer(), config)

        single { pluginsConfig }

        includes(
            pluginsConfig.plugins.map {
                module {
                    with(it) {
                        setupDI(config)
                    }
                }
            }
        )
    }

    override suspend fun Koin.startPlugin() {
        get<Config>().plugins.forEach {
            with(it) {
                startPlugin()
            }
        }
    }
}
