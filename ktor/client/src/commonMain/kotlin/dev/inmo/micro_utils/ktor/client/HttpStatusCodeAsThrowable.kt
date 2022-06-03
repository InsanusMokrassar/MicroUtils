package dev.inmo.micro_utils.ktor.client

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess

inline fun HttpResponse.throwOnUnsuccess(
    unsuccessMessage: () -> String
) {
    if (status.isSuccess()) {
        return
    }

    throw ClientRequestException(this, unsuccessMessage())
}
