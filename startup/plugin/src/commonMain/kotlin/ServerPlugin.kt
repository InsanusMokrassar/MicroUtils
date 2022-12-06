package dev.inmo.micro_utils.startup.plugin

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import org.koin.core.Koin
import org.koin.core.module.Module

@Serializable(ServerPluginSerializer::class)
interface ServerPlugin {
    fun Module.setupDI(config: JsonObject) {}

    suspend fun startPlugin(koin: Koin) {}
}
