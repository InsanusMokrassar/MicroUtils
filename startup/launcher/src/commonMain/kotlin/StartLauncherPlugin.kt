package dev.inmo.micro_utils.startup.launcher

import dev.inmo.kslog.common.i
import dev.inmo.kslog.common.taggedLogger
import dev.inmo.kslog.common.w
import dev.inmo.micro_utils.coroutines.runCatchingSafely
import dev.inmo.micro_utils.startup.plugin.StartPlugin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialFormat
import kotlinx.serialization.StringFormat
import kotlinx.serialization.json.JsonObject
import org.koin.core.Koin
import org.koin.core.module.Module
import org.koin.dsl.binds
import org.koin.dsl.module

/**
 * Default startup plugin. See [setupDI] and [startPlugin] for more info
 */
object StartLauncherPlugin : StartPlugin {
    internal val logger = taggedLogger(this)

    /**
     * Will deserialize [Config] from [config], register it in receiver [Module] (as well as [CoroutineScope] and
     * [kotlinx.serialization.json.Json])
     *
     * Besides, in this method will be called [StartPlugin.setupDI] on each plugin from [Config.plugins]. In case when
     * some plugin will not be loaded correctly it will be reported throw the [logger]
     */
    override fun Module.setupDI(config: JsonObject) {
        val pluginsConfig = defaultJson.decodeFromJsonElement(Config.serializer(), config)

        single { pluginsConfig }
        single { CoroutineScope(Dispatchers.Default) }
        single { defaultJson } binds arrayOf(StringFormat::class, SerialFormat::class)

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

    /**
     * Takes [CoroutineScope] and [Config] from the [koin], and call starting of each plugin from [Config.plugins]
     * ASYNCHRONOUSLY. Just like in [setupDI], in case of fail in some plugin it will be reported using [logger]
     */
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
