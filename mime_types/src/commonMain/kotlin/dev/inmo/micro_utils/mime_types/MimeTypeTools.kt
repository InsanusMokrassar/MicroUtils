package dev.inmo.micro_utils.mime_types

private val mimesCache = mutableMapOf<String, MimeType>().also {
    knownMimeTypes.forEach { mimeType -> it[mimeType.raw] = mimeType }
}

fun mimeType(raw: String) = mimesCache.getOrPut(raw) {
    parseMimeType(raw)
}

internal fun parseMimeType(raw: String): MimeType = CustomMimeType(raw)

