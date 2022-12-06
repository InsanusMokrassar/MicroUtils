package dev.inmo.micro_utils.startup.launcher

import dev.inmo.micro_utils.startup.plugin.StartPlugin
import kotlinx.serialization.Serializable

/**
 * Contains just [List] of [StartPlugin]s. In json this config should look like:
 *
 * ```json
 * {
 *     "plugins": [
 *         "dev.inmo.micro_utils.startup.launcher.HelloWorldPlugin"
 *     ]
 * }
 * ```
 *
 * In the sample above [HelloWorldPlugin] will be loaded during startup of application
 */
@Serializable
data class Config(
    val plugins: List<StartPlugin>
)
