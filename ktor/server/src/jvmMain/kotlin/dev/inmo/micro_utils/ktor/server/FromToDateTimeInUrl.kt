package dev.inmo.micro_utils.ktor.server

import korlibs.time.DateTime
import dev.inmo.micro_utils.ktor.common.FromToDateTime
import io.ktor.http.Parameters

val Parameters.extractFromToDateTime: FromToDateTime
    get() = FromToDateTime(
        get("from") ?.toDoubleOrNull() ?.let { DateTime(it) },
        get("to") ?.toDoubleOrNull() ?.let { DateTime(it) }
    )
