package dev.inmo.micro_utils.ktor.common

fun buildStandardUrl(
    basePart: String,
    subpart: String,
    parameters: QueryParams = emptyMap()
) = "$basePart/$subpart".includeQueryParams(
    parameters
)

fun buildStandardUrl(
    basePart: String,
    subpart: String,
    parameters: List<QueryParam>
) = "$basePart/$subpart".includeQueryParams(
    parameters
)

fun buildStandardUrl(
    basePart: String,
    subpart: String,
    vararg parameters: QueryParam
) = buildStandardUrl(basePart, subpart, parameters.toList())

