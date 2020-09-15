package dev.inmo.micro_utils.pagination

inline fun doWithPagination(
    startPagination: Pagination = FirstPagePagination(),
    requestMaker: (pagination: Pagination) -> Pagination?
) = requestMaker(startPagination).let {
    var pagination = it
    while (pagination != null) {
        pagination = requestMaker(pagination)
    }
}

@Suppress("NOTHING_TO_INLINE")
inline fun PaginationResult<*>.nextPageIfNotEmpty() = if (results.isNotEmpty()) {
    SimplePagination(
        page + 1,
        size
    )
} else {
    null
}

@Suppress("NOTHING_TO_INLINE")
inline fun PaginationResult<*>.thisPageIfNotEmpty(): Pagination? = if (results.isNotEmpty()) {
    this
} else {
    null
}
