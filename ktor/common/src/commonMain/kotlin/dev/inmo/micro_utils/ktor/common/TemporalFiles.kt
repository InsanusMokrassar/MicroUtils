package dev.inmo.micro_utils.ktor.common

import kotlin.jvm.JvmInline

const val DefaultTemporalFilesSubPath = "temp_upload"

@JvmInline
value class TemporalFileId(val string: String)
