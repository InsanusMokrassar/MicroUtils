package dev.inmo.micro_utils.pagination.compose

import androidx.compose.runtime.*
import dev.inmo.micro_utils.common.Optional
import dev.inmo.micro_utils.common.dataOrThrow
import dev.inmo.micro_utils.common.optional
import dev.inmo.micro_utils.pagination.*

class PagedComponentContext<T> internal constructor(
    preset: PaginationResult<T>? = null,
    initialPage: Int,
    size: Int
) {
    internal val iterationState: MutableState<Pair<Int, Pagination>> = mutableStateOf(0 to SimplePagination(preset ?.page ?: initialPage, preset ?.size ?: size))

    internal var dataOptional: PaginationResult<T>? = preset
        private set
    internal val dataState: MutableState<PaginationResult<T>?> = mutableStateOf(dataOptional)

    fun loadNext() {
        iterationState.value = iterationState.value.let {
            if (dataState.value ?.isLastPage == true) return
            (it.first + 1) to it.second.nextPage()
        }
    }
    fun loadPrevious() {
        iterationState.value = iterationState.value.let {
            if (it.second.isFirstPage) return
            (it.first - 1) to SimplePagination(
                it.second.page - 1,
                it.second.size
            )
        }
    }
    fun reload() {
        iterationState.value = iterationState.value.let {
            it.copy(it.first + 1)
        }
    }
}

@Composable
internal fun <T> PagedComponent(
    preload: PaginationResult<T>?,
    initialPage: Int,
    size: Int,
    loader: suspend PagedComponentContext<T>.(Pagination) -> PaginationResult<T>,
    block: @Composable PagedComponentContext<T>.(PaginationResult<T>) -> Unit
) {
    val context = remember { PagedComponentContext(preload, initialPage, size) }

    LaunchedEffect(context.iterationState.value) {
        context.dataState.value = loader(context, context.iterationState.value.second)
    }

    context.dataState.value ?.let {
        context.block(it)
    }
}

@Composable
fun <T> PagedComponent(
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
fun <T> PagedComponent(
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
fun <T> PagedComponent(
    initialPage: Int,
    size: Int,
    loader: suspend PagedComponentContext<T>.(Pagination) -> PaginationResult<T>,
    block: @Composable PagedComponentContext<T>.(PaginationResult<T>) -> Unit
) {
    PagedComponent(null, initialPage, size, loader, block)
}

@Composable
fun <T> PagedComponent(
    size: Int,
    loader: suspend PagedComponentContext<T>.(Pagination) -> PaginationResult<T>,
    block: @Composable PagedComponentContext<T>.(PaginationResult<T>) -> Unit
) {
    PagedComponent(0, size, loader, block)
}
