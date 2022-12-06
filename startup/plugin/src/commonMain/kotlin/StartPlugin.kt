package dev.inmo.micro_utils.startup.plugin

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import org.koin.core.Koin
import org.koin.core.module.Module

/**
 * Default plugin for start of your app
 */
@Serializable(StartPluginSerializer::class)
interface StartPlugin {
    /**
     * This method will be called first to configure [Koin] [Module] related to this plugin. You may use
     * [org.koin.core.scope.Scope.get] in your koin definitions like [Module.single] to retrieve
     * [kotlinx.coroutines.CoroutineScope], [kotlinx.serialization.json.Json] or [dev.inmo.micro_utils.startup.launcher.Config]
     */
    fun Module.setupDI(config: JsonObject) {}

    /**
     * This method will be called after all other [StartPlugin] will [setupDI]
     *
     * It is allowed to lock end of this method in case you require to prevent application to end its run (for example,
     * you are starting some web server)
     *
     * @param koin Will contains everything you will register in [setupDI] (as well as other [StartPlugin]s) and
     * [kotlinx.coroutines.CoroutineScope], [kotlinx.serialization.json.Json] and [dev.inmo.micro_utils.startup.launcher.Config]
     * by their types
     */
    suspend fun startPlugin(koin: Koin) {}
}
