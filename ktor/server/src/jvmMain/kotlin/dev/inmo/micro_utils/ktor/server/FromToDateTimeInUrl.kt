package dev.inmo.micro_utils.ktor.server

import korlibs.time.DateTime
import dev.inmo.micro_utils.ktor.common.FromToDateTime
import io.ktor.http.Parameters

/**
 * Extracts a [FromToDateTime] range from Ktor server [Parameters].
 * Looks for "from" and "to" parameters containing Unix millisecond timestamps.
 *
 * @return A [FromToDateTime] pair extracted from the parameters
 */
val Parameters.extractFromToDateTime: FromToDateTime
    get() = FromToDateTime(
        get("from") ?.toDoubleOrNull() ?.let { DateTime(it) },
        get("to") ?.toDoubleOrNull() ?.let { DateTime(it) }
    )
