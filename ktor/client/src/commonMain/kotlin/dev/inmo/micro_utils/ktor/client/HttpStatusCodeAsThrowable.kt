package dev.inmo.micro_utils.ktor.client

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess

/**
 * Throws a [ClientRequestException] if this [HttpResponse] does not have a successful status code.
 * A status code is considered successful if it's in the 2xx range.
 *
 * @param unsuccessMessage A lambda that provides the error message to use if the response is unsuccessful
 * @throws ClientRequestException if the response status is not successful
 */
inline fun HttpResponse.throwOnUnsuccess(
    unsuccessMessage: () -> String
) {
    if (status.isSuccess()) {
        return
    }

    throw ClientRequestException(this, unsuccessMessage())
}
