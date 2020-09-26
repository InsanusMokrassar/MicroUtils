package dev.inmo.micro_utils.ktor.server

import dev.inmo.micro_utils.pagination.*
import io.ktor.http.Parameters

val Parameters.extractPagination: Pagination
    get() = SimplePagination(
        get("page") ?.toIntOrNull() ?: 0,
        get("size") ?.toIntOrNull() ?: defaultMediumPageSize
    )
