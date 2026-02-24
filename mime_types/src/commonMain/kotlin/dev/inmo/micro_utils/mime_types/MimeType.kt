package dev.inmo.micro_utils.mime_types

import kotlinx.serialization.Serializable

/**
 * Represents a MIME type (Multipurpose Internet Mail Extensions type).
 * A MIME type is a standard way to indicate the nature and format of a document, file, or assortment of bytes.
 *
 * Examples: "text/html", "application/json", "image/png"
 */
@Serializable(MimeTypeSerializer::class)
interface MimeType {
    /**
     * The raw MIME type string (e.g., "text/html", "application/json").
     */
    val raw: String
    
    /**
     * An array of file extensions commonly associated with this MIME type.
     * For example, "text/html" might have extensions ["html", "htm"].
     * Returns an empty array by default if no extensions are known.
     */
    val extensions: Array<String>
        get() = emptyArray()
}
