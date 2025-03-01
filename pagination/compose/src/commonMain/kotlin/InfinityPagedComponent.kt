package dev.inmo.micro_utils.pagination.compose

import androidx.compose.runtime.*
import dev.inmo.micro_utils.common.Optional
import dev.inmo.micro_utils.common.dataOrThrow
import dev.inmo.micro_utils.common.optional
import dev.inmo.micro_utils.pagination.*

class InfinityPagedComponentContext<T> internal constructor(
    preset: List<T>? = null,
    initialPage: Int,
    size: Int
) {
    internal val iterationState: MutableState<Pair<Int, Pagination>> = mutableStateOf(0 to SimplePagination(preset ?.page ?: initialPage, preset ?.size ?: size))

    internal var dataOptional: List<T>? = preset
        private set
    internal val dataState: MutableState<List<T>?> = mutableStateOf(dataOptional)

    fun loadNext() {
        iterationState.value = iterationState.value.let {
            if ((dataState.value as? PaginationResult<*>) ?.isLastPage == true) return
            (it.first + 1) to it.second.nextPage()
        }
    }
    fun reload() {
        iterationState.value = iterationState.value.let {
            (it.first + 1) to (it.second.firstPage())
        }
    }
}

@Composable
internal fun <T> InfinityPagedComponent(
    preload: List<T>?,
    initialPage: Int,
    size: Int,
    loader: suspend PagedComponentContext<T>.(Pagination) -> PaginationResult<T>,
    block: @Composable PagedComponentContext<T>.(List<T>) -> Unit
) {
    val context = remember { InfinityPagedComponentContext(preload, initialPage, size) }

    LaunchedEffect(context.iterationState.value) {
        context.dataState.value = loader(context, context.iterationState.value.second)
    }

    context.dataState.value ?.let {
        context.block()
    }
}

@Composable
fun <T> InfinityPagedComponent(
    preload: PaginationResult<T>,
    loader: suspend PagedComponentContext<T>.(Pagination) -> PaginationResult<T>,
    block: @Composable PagedComponentContext<T>.(PaginationResult<T>) -> Unit
) {
    PagedComponent(
        preload,
        preload.page,
        preload.size,
        loader,
        block
    )
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
