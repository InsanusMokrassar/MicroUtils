package dev.inmo.micro_utils.mime_types

val mimeTypesByExtensions: Map<String, Array<MimeType>> by lazy {
    val extensionsMap = mutableMapOf<String, MutableList<MimeType>>()

    knownMimeTypes.forEach { mimeType ->
        mimeType.extensions.forEach {
            extensionsMap.getOrPut(it) { mutableListOf() }.add(mimeType)
        }
    }

    extensionsMap.mapValues {
        it.value.toTypedArray()
    }
}

inline fun getMimeType(
    stringWithExtension: String,
    selector: (Array<MimeType>) -> MimeType? = { it.firstOrNull() }
) = mimeTypesByExtensions[stringWithExtension.takeLastWhile { it != '.' }] ?.takeIf { it.isNotEmpty() } ?.let(selector)
inline fun getMimeTypeOrAny(
    stringWithExtension: String,
    selector: (Array<MimeType>) -> MimeType? = { it.firstOrNull() }
) = getMimeType(stringWithExtension, selector) ?: KnownMimeTypes.Any
