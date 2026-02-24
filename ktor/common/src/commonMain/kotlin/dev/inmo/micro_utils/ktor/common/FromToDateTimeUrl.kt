package dev.inmo.micro_utils.ktor.common

import korlibs.time.DateTime

/**
 * Type alias representing a date-time range with optional start and end times.
 * First element is the "from" date-time, second is the "to" date-time.
 */
typealias FromToDateTime = Pair<DateTime?, DateTime?>

/**
 * Converts this [FromToDateTime] range to URL query parameters.
 * Creates "from" and "to" query parameters with Unix millisecond timestamps.
 *
 * @return A map of query parameters representing the date-time range
 */
val FromToDateTime.asFromToUrlPart: QueryParams
    get() = mapOf(
        "from" to first ?.unixMillis ?.toString(),
        "to" to second ?.unixMillis ?.toString()
    )

/**
 * Extracts a [FromToDateTime] range from URL query parameters.
 * Looks for "from" and "to" parameters containing Unix millisecond timestamps.
 *
 * @return A [FromToDateTime] pair extracted from the query parameters
 */
val QueryParams.extractFromToDateTime: FromToDateTime
    get() = FromToDateTime(
        get("from") ?.toDoubleOrNull() ?.let { DateTime(it) },
        get("to") ?.toDoubleOrNull() ?.let { DateTime(it) }
    )
