package dev.inmo.micro_utils.ktor.common

import com.soywiz.klock.DateTime

typealias FromToDateTime = Pair<DateTime?, DateTime?>

val FromToDateTime.asFromToUrlPart: QueryParams
    get() = mapOf(
        "from" to first ?.unixMillis ?.toString(),
        "to" to second ?.unixMillis ?.toString()
    )

val QueryParams.extractFromToDateTime: FromToDateTime
    get() = FromToDateTime(
        get("from") ?.toDoubleOrNull() ?.let { DateTime(it) },
        get("to") ?.toDoubleOrNull() ?.let { DateTime(it) }
    )
