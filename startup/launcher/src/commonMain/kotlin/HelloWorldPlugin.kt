package dev.inmo.micro_utils.startup.launcher

import dev.inmo.kslog.common.i
import dev.inmo.kslog.common.logger
import dev.inmo.micro_utils.startup.plugin.StartPlugin
import org.koin.core.Koin

object HelloWorldPlugin : StartPlugin {
    override suspend fun startPlugin(koin: Koin) {
        super.startPlugin(koin)
        logger.i("Hello world")
    }
}
