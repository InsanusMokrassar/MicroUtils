package dev.inmo.micro_utils.ktor.client

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

suspend inline fun <reified T : Any> HttpResponse.bodyOrNull(
    statusFilter: (HttpResponse) -> Boolean = { it.status == HttpStatusCode.OK }
) = takeIf(statusFilter) ?.body<T>()

suspend inline fun <reified T : Any> HttpResponse.bodyOrNullOnNoContent() = bodyOrNull<T> {
    it.status != HttpStatusCode.NoContent
}
