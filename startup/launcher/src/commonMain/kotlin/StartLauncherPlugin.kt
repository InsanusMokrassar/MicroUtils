package dev.inmo.micro_utils.startup.launcher

import dev.inmo.kslog.common.i
import dev.inmo.kslog.common.taggedLogger
import dev.inmo.kslog.common.w
import dev.inmo.micro_utils.coroutines.runCatchingSafely
import dev.inmo.micro_utils.startup.launcher.StartLauncherPlugin.setupDI
import dev.inmo.micro_utils.startup.launcher.StartLauncherPlugin.startPlugin
import dev.inmo.micro_utils.startup.plugin.StartPlugin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialFormat
import kotlinx.serialization.StringFormat
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.binds
import org.koin.dsl.module

/**
 * Default startup plugin. See [setupDI] and [startPlugin] for more info
 */
object StartLauncherPlugin : StartPlugin {
    internal val logger = taggedLogger(this)

    fun Module.setupDI(config: Config, rawJsonObject: JsonObject? = null) {
        val rawJsonObject = rawJsonObject ?: defaultJson.encodeToJsonElement(Config.serializer(), config).jsonObject

        single { rawJsonObject }
        single { config }
        single { CoroutineScope(Dispatchers.Default) }
        single { defaultJson } binds arrayOf(StringFormat::class, SerialFormat::class)

        includes(
            config.plugins.mapNotNull {
                val pluginName = it::class.simpleName ?: it.toString()
                runCatching {
                    logger.i { "Start koin module registration for $pluginName" }
                    module {
                        with(it) {
                            setupDI(rawJsonObject)
                        }
                    }
                }.onFailure { e ->
                    logger.w("Unable to register koin module of $pluginName", e)
                }.onSuccess {
                    logger.i("Successfully registered koin module of $pluginName")
                }.getOrNull()
            }
        )
    }

    /**
     * Will deserialize [Config] from [config], register it in receiver [Module] (as well as [CoroutineScope] and
     * [kotlinx.serialization.json.Json])
     *
     * Besides, in this method will be called [StartPlugin.setupDI] on each plugin from [Config.plugins]. In case when
     * some plugin will not be loaded correctly it will be reported throw the [logger]
     */
    override fun Module.setupDI(config: JsonObject) {
        logger.i("Koin for current module has started setup")
        setupDI(
            defaultJson.decodeFromJsonElement(Config.serializer(), config),
            config
        )
        logger.i("Koin for current module has been setup")
    }

    /**
     * Takes [CoroutineScope] and [Config] from the [koin], and call starting of each plugin from [Config.plugins]
     * ASYNCHRONOUSLY. Just like in [setupDI], in case of fail in some plugin it will be reported using [logger]
     */
    override suspend fun startPlugin(koin: Koin) {
        logger.i("Start starting of subplugins")
        val scope = koin.get<CoroutineScope>()
        koin.get<Config>().plugins.map { plugin ->
            val pluginName = plugin::class.simpleName ?: plugin.toString()
            scope.launch {
                runCatchingSafely {
                    logger.i("Start loading of $pluginName")
                    with(plugin) {
                        startPlugin(koin)
                    }
                }.onFailure { e ->
                    logger.w("Unable to start plugin $pluginName", e)
                }.onSuccess {
                    logger.i("Complete loading of $pluginName")
                }
            }
        }.joinAll()
        logger.i("Complete subplugins start")
    }

    /**
     * Will create [KoinApplication], init, load modules using [StartLauncherPlugin] and start plugins using the same base
     * plugin. It is basic [start] method which accepts both [config] and [rawConfig] which suppose to be the same or
     * at least [rawConfig] must contain serialized variant of [config]
     *
     * @param rawConfig It is expected that this [JsonObject] will contain serialized [Config] ([StartLauncherPlugin] will
     * deserialize it in its [StartLauncherPlugin.setupDI]
     */
    suspend fun start(config: Config, rawConfig: JsonObject) {

        logger.i("Start initialization")
        val koinApp = KoinApplication.init()
        koinApp.modules(
            module {
                setupDI(config, rawConfig)
            }
        )
        logger.i("Modules loaded")
        startKoin(koinApp)
        logger.i("Koin started")
        startPlugin(koinApp.koin)
        logger.i("App has been setup")

    }

    /**
     * Call [start] with deserialized [Config] as config and [rawConfig] as is
     *
     * @param rawConfig It is expected that this [JsonObject] will contain serialized [Config]
     */
    suspend fun start(rawConfig: JsonObject) {

        start(defaultJson.decodeFromJsonElement(Config.serializer(), rawConfig), rawConfig)

    }

    /**
     * Call [start] with deserialized [Config] as is and serialize it to [JsonObject] to pass as the first parameter
     * to the basic [start] method
     *
     * @param config Will be converted to [JsonObject] as raw config. That means that all plugins from [config] will
     * receive serialized version of [config] in [StartPlugin.setupDI] method
     */
    suspend fun start(config: Config) {

        start(config, defaultJson.encodeToJsonElement(Config.serializer(), config).jsonObject)

    }
}
