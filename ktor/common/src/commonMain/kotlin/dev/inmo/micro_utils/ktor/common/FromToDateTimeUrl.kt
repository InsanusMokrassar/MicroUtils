package dev.inmo.micro_utils.ktor.common

import kotlinx.datetime.Instant

typealias FromToDateTime = Pair<Instant?, Instant?>

val FromToDateTime.asFromToUrlPart: QueryParams
    get() = mapOf(
        "from" to first ?.toEpochMilliseconds() ?.toString(),
        "to" to second ?.toEpochMilliseconds() ?.toString()
    )

val QueryParams.extractFromToDateTime: FromToDateTime
    get() = FromToDateTime(
        get("from") ?.run { toLongOrNull() ?: (toDoubleOrNull() ?.toLong()) } ?.let { Instant.fromEpochMilliseconds(it) },
        get("to") ?.run { toLongOrNull() ?: (toDoubleOrNull() ?.toLong()) } ?.let { Instant.fromEpochMilliseconds(it) }
    )
