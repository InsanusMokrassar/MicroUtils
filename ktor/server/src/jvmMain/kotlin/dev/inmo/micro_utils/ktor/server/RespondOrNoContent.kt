package dev.inmo.micro_utils.ktor.server

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond

/**
 * Responds with the given [data] if it's not null, or responds with [HttpStatusCode.NoContent] (204) if it's null.
 * This is useful for API endpoints that may return empty results.
 *
 * @param T The type of data to respond with
 * @param data The data to respond with, or null to respond with No Content
 */
suspend inline fun <reified T : Any> ApplicationCall.respondOrNoContent(
    data: T?
) {
    if (data == null) {
        respond(HttpStatusCode.NoContent)
    } else {
        respond(data)
    }
}
