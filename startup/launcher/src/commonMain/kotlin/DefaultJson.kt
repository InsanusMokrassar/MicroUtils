package dev.inmo.micro_utils.startup.launcher

import kotlinx.serialization.json.Json

val defaultJson = Json {
    ignoreUnknownKeys = true
}
