package dev.inmo.micro_utils.pagination

import io.ktor.application.ApplicationCall
import io.ktor.http.Parameters

val Parameters.extractPagination: Pagination
    get() = SimplePagination(
        get("page") ?.toIntOrNull() ?: 0,
        get("size") ?.toIntOrNull() ?: defaultPaginationPageSize
    )

val ApplicationCall.extractPagination: Pagination
    get() = request.queryParameters.extractPagination

