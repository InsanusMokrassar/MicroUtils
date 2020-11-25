package dev.inmo.micro_utils.ktor.common

typealias QueryParam = Pair<String, String?>
typealias QueryParams = Map<String, String?>

/**
 * Create query part which includes key=value pairs separated with &
 */
val QueryParams.asUrlQuery: String
    get() = keys.joinToString("&") { "${it}${get(it) ?.let { value -> "=$value" }}" }

/**
 * Create query part which includes key=value pairs separated with &
 */
val List<QueryParam>.asUrlQuery: String
    get() = joinToString("&") { (key, value) -> "${key}${value ?.let { _ -> "=$value" }}" }

/**
 * Create query part which includes key=value pairs separated with & and attach to receiver
 */
fun String.includeQueryParams(
    queryParams: QueryParams
): String = "$this${if(queryParams.isNotEmpty()) "${if (contains("?")) "&" else "?"}${queryParams.asUrlQuery}" else ""}"

/**
 * Create query part which includes key=value pairs separated with & and attach to receiver
 */
fun String.includeQueryParams(
    queryParams: List<QueryParam>
): String = "$this${if (contains("?")) "&" else "?"}${queryParams.asUrlQuery}"

val String.parseUrlQuery: QueryParams
    get() = split("&").map {
        it.split("=").let { pair ->
            pair.first() to pair.getOrNull(1)
        }
    }.toMap()
