package dev.inmo.micro_utils.mime_types

import kotlinx.serialization.Serializable

@Serializable(MimeTypeSerializer::class)
interface MimeType {
    val raw: String
}
