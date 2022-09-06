package dev.inmo.micro_utils.ktor.server

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond

suspend inline fun <reified T : Any> ApplicationCall.respondOrNoContent(
    data: T?
) {
    if (data == null) {
        respond(HttpStatusCode.NoContent)
    } else {
        respond(data)
    }
}
