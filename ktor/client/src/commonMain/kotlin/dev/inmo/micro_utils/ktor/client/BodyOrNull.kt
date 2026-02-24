package dev.inmo.micro_utils.ktor.client

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

/**
 * Returns the response body as type [T] if the [statusFilter] condition is met, otherwise returns null.
 * By default, the filter checks if the status code is [HttpStatusCode.OK].
 *
 * @param T The type to deserialize the response body to
 * @param statusFilter A predicate to determine if the body should be retrieved. Defaults to checking for OK status
 * @return The deserialized body of type [T], or null if the filter condition is not met
 */
suspend inline fun <reified T : Any> HttpResponse.bodyOrNull(
    statusFilter: (HttpResponse) -> Boolean = { it.status == HttpStatusCode.OK }
) = takeIf(statusFilter) ?.body<T>()

/**
 * Returns the response body as type [T] if the status code is not [HttpStatusCode.NoContent], otherwise returns null.
 * This is useful for handling responses that may return 204 No Content.
 *
 * @param T The type to deserialize the response body to
 * @return The deserialized body of type [T], or null if the status is No Content
 */
suspend inline fun <reified T : Any> HttpResponse.bodyOrNullOnNoContent() = bodyOrNull<T> {
    it.status != HttpStatusCode.NoContent
}
