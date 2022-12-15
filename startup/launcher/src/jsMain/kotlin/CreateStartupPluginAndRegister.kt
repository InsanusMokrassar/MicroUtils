package dev.inmo.micro_utils.startup.launcher

import dev.inmo.micro_utils.startup.plugin.StartPlugin
import dev.inmo.micro_utils.startup.plugin.StartPluginSerializer

/**
 * Creates [T] using [block], register it in [StartPluginSerializer] using its [StartPluginSerializer.registerPlugin]
 * and returns created by [block] plugin
 */
inline fun <T : StartPlugin> createStartupPluginAndRegister(name: String, block: () -> T): T {
    val plugin = block()
    StartPluginSerializer.registerPlugin(name, plugin)
    return plugin
}
