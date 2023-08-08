package group_name.module_name.client

import group_name.module_name.common.CommonAndroidPlugin
import group_name.module_name.common.CommonAndroidPlugin.setupDI
import dev.inmo.micro_utils.startup.plugin.StartPlugin
import kotlinx.serialization.json.JsonObject
import org.koin.core.Koin
import org.koin.core.module.Module

object ClientAndroidPlugin : StartPlugin {
    override fun Module.setupDI(config: JsonObject) {
        with(CommonAndroidPlugin) { setupDI(config) }
        with(ClientPlugin) { setupDI(config) }
    }

    override suspend fun startPlugin(koin: Koin) {
        super.startPlugin(koin)
        CommonAndroidPlugin.startPlugin(koin)
        ClientPlugin.startPlugin(koin)
    }
}
