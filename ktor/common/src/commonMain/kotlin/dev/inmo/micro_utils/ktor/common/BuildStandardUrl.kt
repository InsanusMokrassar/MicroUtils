package dev.inmo.micro_utils.ktor.common

import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
fun buildStandardUrl(
    basePart: String,
    subpart: String,
    parameters: QueryParams = emptyMap()
) = "$basePart/$subpart".includeQueryParams(
    parameters
)

@JsExport
@JsName("buildStandardUrlWithParametersList")
fun buildStandardUrl(
    basePart: String,
    subpart: String,
    parameters: List<QueryParam>
) = "$basePart/$subpart".includeQueryParams(
    parameters
)

@JsExport
@JsName("buildStandardUrlWithParametersVararg")
fun buildStandardUrl(
    basePart: String,
    subpart: String,
    vararg parameters: QueryParam
) = buildStandardUrl(basePart, subpart, parameters.toList())

