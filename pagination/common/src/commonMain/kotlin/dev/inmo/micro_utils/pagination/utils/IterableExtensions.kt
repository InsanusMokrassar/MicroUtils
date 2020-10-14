package dev.inmo.micro_utils.pagination.utils

import dev.inmo.micro_utils.pagination.*
import kotlin.js.JsExport

@JsExport
fun <T> Iterable<T>.paginate(with: Pagination): PaginationResult<T> {
    var i = 0
    val result = mutableListOf<T>()
    val lowerIndex = with.firstIndex
    val greatestIndex = with.lastIndex
    for (item in this) {
        when {
            i < lowerIndex || i > greatestIndex -> i++
            else -> {
                result.add(item)
                i++
            }
        }
    }

    return result.createPaginationResult(with, i.toLong())
}

@JsExport
fun <T> List<T>.paginate(with: Pagination): PaginationResult<T> {
    return subList(with.firstIndex, with.lastIndex + 1).createPaginationResult(
        with,
        size.toLong()
    )
}

@JsExport
fun <T> Set<T>.paginate(with: Pagination): PaginationResult<T> {
    return this.drop(with.firstIndex).take(with.size).createPaginationResult(
        with,
        size.toLong()
    )
}
