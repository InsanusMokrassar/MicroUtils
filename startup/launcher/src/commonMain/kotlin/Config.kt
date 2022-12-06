package dev.inmo.micro_utils.startup.launcher

import dev.inmo.micro_utils.startup.plugin.StartPlugin
import kotlinx.serialization.Serializable

@Serializable
data class Config(
    val plugins: List<StartPlugin>
)
