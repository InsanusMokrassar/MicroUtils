package dev.inmo.micro_utils.startup.plugin

import com.benasher44.uuid.uuid4
import kotlin.reflect.KClass

/**
 * Creates [T] using [block], register it in [StartPluginSerializer] using its [StartPluginSerializer.registerPlugin]
 * and returns created by [block] plugin
 *
 * @param name Will be used as a key for registration in [StartPluginSerializer] and will be passed to the [block] as
 * parameter
 */
inline fun <T : StartPlugin> createStartupPluginAndRegister(
    name: String = uuid4().toString(),
    block: (String) -> T
): T {
    val plugin = block(name)
    StartPluginSerializer.registerPlugin(name, plugin)
    return plugin
}

/**
 * Creates [T] using [block], register it in [StartPluginSerializer] using its [StartPluginSerializer.registerPlugin]
 * and returns created by [block] plugin
 */
inline fun <T : StartPlugin> createStartupPluginAndRegister(
    kClass: KClass<T>,
    name: String = uuid4().toString(),
    block: (String) -> T
): T = createStartupPluginAndRegister("${kClass.simpleName}_$name", block)

/**
 * Creates [T] using [block], register it in [StartPluginSerializer] using its [StartPluginSerializer.registerPlugin]
 * and returns created by [block] plugin
 */
inline fun <reified T : StartPlugin> createStartupPluginAndRegister(
    block: (String) -> T
): T = createStartupPluginAndRegister(T::class, uuid4().toString(), block)
