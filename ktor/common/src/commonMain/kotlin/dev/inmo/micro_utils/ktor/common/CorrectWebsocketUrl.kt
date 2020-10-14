package dev.inmo.micro_utils.ktor.common

import kotlin.js.JsExport

private val schemaRegex = Regex("^[^:]*://")

@JsExport
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
