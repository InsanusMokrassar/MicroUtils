package dev.inmo.micro_utils.ktor.server

import com.soywiz.klock.DateTime
import dev.inmo.micro_utils.ktor.common.FromToDateTime
import io.ktor.http.Parameters

val Parameters.extractFromToDateTime: FromToDateTime
    get() = FromToDateTime(
        get("from") ?.toDoubleOrNull() ?.let { DateTime(it) },
        get("to") ?.toDoubleOrNull() ?.let { DateTime(it) }
    )
