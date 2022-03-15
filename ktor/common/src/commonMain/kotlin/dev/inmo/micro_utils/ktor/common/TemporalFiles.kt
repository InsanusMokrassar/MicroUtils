package dev.inmo.micro_utils.ktor.common

import kotlin.jvm.JvmInline
import kotlinx.serialization.Serializable

const val DefaultTemporalFilesSubPath = "temp_upload"

@Serializable
@JvmInline
value class TemporalFileId(val string: String)
