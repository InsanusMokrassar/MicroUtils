package dev.inmo.micro_utils.ktor.server

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond

/**
 * Retrieves a parameter value by [field] name from the request parameters.
 * If the parameter is not present, responds with [HttpStatusCode.BadRequest] (400) and an error message.
 *
 * @param field The name of the parameter to retrieve
 * @return The parameter value, or null if not found (after sending error response)
 */
suspend fun ApplicationCall.getParameterOrSendError(
    field: String
) = parameters[field].also {
    if (it == null) {
        respond(HttpStatusCode.BadRequest, "Request must contains $field")
    }
}

/**
 * Retrieves all parameter values by [field] name from the request parameters.
 * If the parameter is not present, responds with [HttpStatusCode.BadRequest] (400) and an error message.
 *
 * @param field The name of the parameter to retrieve
 * @return A list of parameter values, or null if not found (after sending error response)
 */
suspend fun ApplicationCall.getParametersOrSendError(
    field: String
) = parameters.getAll(field).also {
    if (it == null) {
        respond(HttpStatusCode.BadRequest, "Request must contains $field")
    }
}

/**
 * Retrieves a query parameter value by [field] name from the request.
 *
 * @param field The name of the query parameter to retrieve
 * @return The query parameter value, or null if not found
 */
fun ApplicationCall.getQueryParameter(
    field: String
) = request.queryParameters[field]

/**
 * Retrieves all query parameter values by [field] name from the request.
 *
 * @param field The name of the query parameter to retrieve
 * @return A list of query parameter values, or null if not found
 */
fun ApplicationCall.getQueryParameters(
    field: String
) = request.queryParameters.getAll(field)

/**
 * Retrieves a query parameter value by [field] name from the request.
 * If the parameter is not present, responds with [HttpStatusCode.BadRequest] (400) and an error message.
 *
 * @param field The name of the query parameter to retrieve
 * @return The query parameter value, or null if not found (after sending error response)
 */
suspend fun ApplicationCall.getQueryParameterOrSendError(
    field: String
) = getQueryParameter(field).also {
    if (it == null) {
        respond(HttpStatusCode.BadRequest, "Request query parameters must contains $field")
    }
}

/**
 * Retrieves all query parameter values by [field] name from the request.
 * If the parameter is not present, responds with [HttpStatusCode.BadRequest] (400) and an error message.
 *
 * @param field The name of the query parameter to retrieve
 * @return A list of query parameter values, or null if not found (after sending error response)
 */
suspend fun ApplicationCall.getQueryParametersOrSendError(
    field: String
) = getQueryParameters(field).also {
    if (it == null) {
        respond(HttpStatusCode.BadRequest, "Request query parameters must contains $field")
    }
}
