package dev.inmo.micro_utils.pagination.compose

import androidx.compose.runtime.*
import dev.inmo.micro_utils.pagination.*

class InfinityPagedComponentContext<T> internal constructor(
    page: Int,
    size: Int
) {
    internal val iterationState: MutableState<Pair<Int, Pagination>> = mutableStateOf(0 to SimplePagination(page, size))

    internal val dataState: MutableState<List<T>?> = mutableStateOf(null)

    fun loadNext() {
        iterationState.value = iterationState.value.let {
            if ((dataState.value as? PaginationResult<*>) ?.isLastPage == true) return
            (it.first + 1) to it.second.nextPage()
        }
    }
    fun reload() {
        dataState.value = null
        iterationState.value = iterationState.value.let {
            (it.first + 1) to (it.second.firstPage())
        }
    }
}

@Composable
internal fun <T> InfinityPagedComponent(
    page: Int,
    size: Int,
    loader: suspend InfinityPagedComponentContext<T>.(Pagination) -> PaginationResult<T>,
    block: @Composable InfinityPagedComponentContext<T>.(List<T>?) -> Unit
) {
    val context = remember { InfinityPagedComponentContext<T>(page, size) }

    LaunchedEffect(context.iterationState.value) {
        context.dataState.value = (context.dataState.value ?: emptyList()) + loader(context, context.iterationState.value.second).results
    }

    context.dataState.value ?.let {
        context.block(context.dataState.value)
    }
}

@Composable
fun <T> InfinityPagedComponent(
    pageInfo: Pagination,
    loader: suspend PagedComponentContext<T>.(Pagination) -> PaginationResult<T>,
    block: @Composable PagedComponentContext<T>.(PaginationResult<T>) -> Unit
) {
    PagedComponent(
        null,
        pageInfo.page,
        pageInfo.size,
        loader,
        block
    )
}

@Composable
fun <T> InfinityPagedComponent(
    initialPage: Int,
    size: Int,
    loader: suspend PagedComponentContext<T>.(Pagination) -> PaginationResult<T>,
    block: @Composable PagedComponentContext<T>.(PaginationResult<T>) -> Unit
) {
    PagedComponent(null, initialPage, size, loader, block)
}

@Composable
fun <T> InfinityPagedComponent(
    size: Int,
    loader: suspend PagedComponentContext<T>.(Pagination) -> PaginationResult<T>,
    block: @Composable PagedComponentContext<T>.(PaginationResult<T>) -> Unit
) {
    PagedComponent(0, size, loader, block)
}
