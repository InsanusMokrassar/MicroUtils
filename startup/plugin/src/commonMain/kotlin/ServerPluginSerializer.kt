package dev.inmo.micro_utils.startup.plugin

import kotlinx.serialization.KSerializer

expect object ServerPluginSerializer : KSerializer<ServerPlugin>
