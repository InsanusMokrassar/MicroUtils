package dev.inmo.micro_utils.ktor.common

import com.soywiz.klock.DateTime
import kotlin.js.JsExport

typealias FromToDateTime = Pair<DateTime?, DateTime?>

@JsExport
val FromToDateTime.asFromToUrlPart: QueryParams
    get() = mapOf(
        "from" to first ?.unixMillis ?.toString(),
        "to" to second ?.unixMillis ?.toString()
    )

@JsExport
val QueryParams.extractFromToDateTime: FromToDateTime
    get() = FromToDateTime(
        get("from") ?.toDoubleOrNull() ?.let { DateTime(it) },
        get("to") ?.toDoubleOrNull() ?.let { DateTime(it) }
    )
