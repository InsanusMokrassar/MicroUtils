package dev.inmo.micro_utils.ktor.common

private val schemaRegex = Regex("^[^:]*://")

/**
 * Converts this string to a correct WebSocket URL by ensuring it starts with "ws://" scheme.
 * If the URL already starts with "ws", it is returned unchanged.
 * If the URL contains a scheme (e.g., "http://"), it is replaced with "ws://".
 * If the URL has no scheme, "ws://" is prepended.
 *
 * @return A properly formatted WebSocket URL
 */
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
