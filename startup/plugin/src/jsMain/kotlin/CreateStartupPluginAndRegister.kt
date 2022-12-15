package dev.inmo.micro_utils.startup.plugin

/**
 * Creates [T] using [block], register it in [StartPluginSerializer] using its [StartPluginSerializer.registerPlugin]
 * and returns created by [block] plugin
 *
 * @param name Will be used as a key for registration in [StartPluginSerializer] and will be passed to the [block] as
 * parameter
 */
inline fun <T : StartPlugin> createStartupPluginAndRegister(name: String, block: (String) -> T): T {
    val plugin = block(name)
    StartPluginSerializer.registerPlugin(name, plugin)
    return plugin
}
