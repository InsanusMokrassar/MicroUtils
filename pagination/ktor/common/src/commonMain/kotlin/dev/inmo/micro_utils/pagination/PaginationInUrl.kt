package dev.inmo.micro_utils.pagination

import kotlin.js.JsExport

@JsExport
const val paginationPageKey = "ppage"
@JsExport
const val paginationSizeKey = "psize"

@JsExport
val Pagination.asUrlQueryParts
    get() = mapOf(
        paginationPageKey to page.toString(),
        paginationSizeKey to size.toString()
    )

@JsExport
val Pagination.asUrlQueryArrayParts
    get() = arrayOf(
        paginationPageKey to page.toString(),
        paginationSizeKey to size.toString()
    )

@JsExport
val Map<String, String?>.extractPagination: Pagination
    get() = SimplePagination(
        get(paginationPageKey) ?.toIntOrNull() ?: 0,
        get(paginationSizeKey) ?.toIntOrNull() ?: defaultMediumPageSize
    )

