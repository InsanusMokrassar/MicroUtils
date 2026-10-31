package dev.inmo.micro_utils.mime_types

/**
 * A custom implementation of [MimeType] that wraps a raw MIME type string.
 * Use this when you need to work with MIME types that aren't defined in the standard set.
 *
 * @param raw The raw MIME type string (e.g., "application/custom", "text/x-custom")
 */
data class CustomMimeType(override val raw: String) : MimeType
