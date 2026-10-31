package dev.inmo.micro_utils.ktor.common

import kotlin.jvm.JvmInline
import kotlinx.serialization.Serializable

/**
 * The default subdirectory path for storing temporal files during upload operations.
 */
const val DefaultTemporalFilesSubPath = "temp_upload"

/**
 * A value class representing a unique identifier for a temporal file.
 * Temporal files are typically used for temporary storage during file upload/processing operations.
 *
 * @param string The string representation of the temporal file ID
 */
@Serializable
@JvmInline
value class TemporalFileId(val string: String)
