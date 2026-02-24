package dev.inmo.micro_utils.ktor.common

/**
 * Builds a standard URL by combining a base part, subpart, and optional query parameters.
 * The base and subpart are joined with a '/', and query parameters are appended.
 *
 * @param basePart The base part of the URL (e.g., "https://example.com/api")
 * @param subpart The subpart of the URL (e.g., "users")
 * @param parameters Query parameters as a map. Defaults to an empty map
 * @return The complete URL string with query parameters
 */
fun buildStandardUrl(
    basePart: String,
    subpart: String,
    parameters: QueryParams = emptyMap()
) = "$basePart/$subpart".includeQueryParams(
    parameters
)

/**
 * Builds a standard URL by combining a base part, subpart, and query parameters as a list.
 * The base and subpart are joined with a '/', and query parameters are appended.
 *
 * @param basePart The base part of the URL (e.g., "https://example.com/api")
 * @param subpart The subpart of the URL (e.g., "users")
 * @param parameters Query parameters as a list of key-value pairs
 * @return The complete URL string with query parameters
 */
fun buildStandardUrl(
    basePart: String,
    subpart: String,
    parameters: List<QueryParam>
) = "$basePart/$subpart".includeQueryParams(
    parameters
)

/**
 * Builds a standard URL by combining a base part, subpart, and vararg query parameters.
 * The base and subpart are joined with a '/', and query parameters are appended.
 *
 * @param basePart The base part of the URL (e.g., "https://example.com/api")
 * @param subpart The subpart of the URL (e.g., "users")
 * @param parameters Query parameters as vararg key-value pairs
 * @return The complete URL string with query parameters
 */
fun buildStandardUrl(
    basePart: String,
    subpart: String,
    vararg parameters: QueryParam
) = buildStandardUrl(basePart, subpart, parameters.toList())

