package dev.inmo.micro_utils.ktor.server

import dev.inmo.micro_utils.ktor.common.FromToDateTime
import io.ktor.http.Parameters
import kotlinx.datetime.Instant

val Parameters.extractFromToDateTime: FromToDateTime
    get() = FromToDateTime(
        get("from") ?.run { toLongOrNull() ?: (toDoubleOrNull() ?.toLong()) } ?.let { Instant.fromEpochMilliseconds(it) },
        get("to") ?.run { toLongOrNull() ?: (toDoubleOrNull() ?.toLong()) } ?.let { Instant.fromEpochMilliseconds(it) }
    )
