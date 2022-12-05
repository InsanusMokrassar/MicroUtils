package dev.inmo.micro_utils.startup.launcher

import dev.inmo.kslog.common.i
import dev.inmo.kslog.common.taggedLogger
import dev.inmo.kslog.common.w
import dev.inmo.micro_utils.coroutines.runCatchingSafely
import dev.inmo.micro_utils.startup.plugin.ServerPlugin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonObject
import org.koin.core.Koin
import org.koin.core.module.Module
import org.koin.dsl.module

object StartupLauncher : ServerPlugin {
    internal val logger = taggedLogger(this)
    override fun Module.setupDI(config: JsonObject) {
        val pluginsConfig = defaultJson.decodeFromJsonElement(Config.serializer(), config)

        single { pluginsConfig }
        single { CoroutineScope(Dispatchers.Default) }

        includes(
            pluginsConfig.plugins.mapNotNull {
                runCatching {
                    module {
                        with(it) {
                            setupDI(config)
                        }
                    }
                }.onFailure { e ->
                    logger.w("Unable to load DI part of $it", e)
                }.getOrNull()
            }
        )
    }

    override suspend fun startPlugin(koin: Koin) {
        val scope = koin.get<CoroutineScope>()
        koin.get<Config>().plugins.map { plugin ->
            scope.launch {
                runCatchingSafely {
                    logger.i("Start loading of $plugin")
                    with(plugin) {
                        startPlugin(koin)
                    }
                }.onFailure { e ->
                    logger.w("Unable to load bot part of $plugin", e)
                }.onSuccess {
                    logger.i("Complete loading of $plugin")
                }
            }
        }.joinAll()
    }
}
