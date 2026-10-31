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

inline fun <T, PR: PaginationResult<T>> PR.nextPageIfTrue(condition: PR.() -> Boolean) = if (condition()) {
    SimplePagination(
        page + 1,
        size
    )
} else {
    null
}

inline fun <T, PR: PaginationResult<T>> PR.thisPageIfTrue(condition: PR.() -> Boolean): PR? = if (condition()) {
    this
} else {
    null
}

fun PaginationResult<*>.nextPageIfNotEmpty() = nextPageIfTrue { results.isNotEmpty() }

fun <T> PaginationResult<T>.thisPageIfNotEmpty(): PaginationResult<T>? = thisPageIfTrue { results.isNotEmpty() }

fun <T> PaginationResult<T>.currentPageIfNotEmpty() = thisPageIfNotEmpty()


fun PaginationResult<*>.nextPageIfNotEmptyOrLastPage() = nextPageIfTrue { results.isNotEmpty() && !this.isLastPage }

fun <T> PaginationResult<T>.thisPageIfNotEmptyOrLastPage(): PaginationResult<T>? = thisPageIfTrue  { results.isNotEmpty() && !this.isLastPage }

fun <T> PaginationResult<T>.currentPageIfNotEmptyOrLastPage() = thisPageIfNotEmptyOrLastPage()


fun PaginationResult<*>.nextPageIfNotLastPage() = nextPageIfTrue { !this.isLastPage }

fun <T> PaginationResult<T>.thisPageIfNotLastPage(): PaginationResult<T>? = thisPageIfTrue  { !this.isLastPage }

fun <T> PaginationResult<T>.currentPageIfNotLastPage() = thisPageIfNotLastPage()
