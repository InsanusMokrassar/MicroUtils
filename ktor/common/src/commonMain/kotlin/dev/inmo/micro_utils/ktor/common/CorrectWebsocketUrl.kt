package dev.inmo.micro_utils.ktor.common

private val schemaRegex = Regex("^[^:]*://")

val String.asCorrectWebSocketUrl: String
    get() = if (startsWith("ws")) {
        this
    } else {
        if (contains("://")) {
            replace(schemaRegex, "ws://")
        } else {
            "ws://$this"
        }
    }
