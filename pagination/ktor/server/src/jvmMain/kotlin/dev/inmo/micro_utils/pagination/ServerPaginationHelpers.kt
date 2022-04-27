package dev.inmo.micro_utils.pagination

import io.ktor.http.Parameters
import io.ktor.server.application.ApplicationCall

val Parameters.extractPagination: Pagination
    get() = SimplePagination(
        get(paginationPageKey) ?.toIntOrNull() ?: 0,
        get(paginationSizeKey) ?.toIntOrNull() ?: defaultPaginationPageSize
    )

val ApplicationCall.extractPagination: Pagination
    get() = request.queryParameters.extractPagination

