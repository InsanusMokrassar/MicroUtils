package dev.inmo.micro_utils.pagination.compose

import androidx.compose.runtime.*
import dev.inmo.micro_utils.common.Optional
import dev.inmo.micro_utils.common.dataOrThrow
import dev.inmo.micro_utils.common.optional
import dev.inmo.micro_utils.pagination.*

/**
 * Context for managing paginated data in a Compose UI.
 *
 * @param T The type of data being paginated.
 * @property iterationState Holds the current pagination state and iteration count.
 * @property dataOptional Stores the optional preloaded pagination result.
 * @property dataState Stores the current pagination result.
 * @constructor Internal constructor for setting up pagination.
 * @param preset Optional preset pagination result.
 * @param initialPage Initial page number.
 * @param size Number of items per page.
 */
class PagedComponentContext<T> internal constructor(
    preset: PaginationResult<T>? = null,
    initialPage: Int,
    size: Int
) {
    internal val iterationState: MutableState<Pagination> = mutableStateOf(SimplePagination(preset?.page ?: initialPage, preset?.size ?: size))
    
    internal var dataOptional: PaginationResult<T>? = preset
        private set
    internal val dataState: MutableState<PaginationResult<T>?> = mutableStateOf(dataOptional)

    /**
     * Loads the next page of data. If the last page is reached, this function returns early.
     */
    fun loadNext() {
        iterationState.value = iterationState.value.let {
            if (dataState.value ?.isLastPage == true) return
            it.nextPage()
        }
    }

    /**
     * Loads the previous page of data if available.
     */
    fun loadPrevious() {
        iterationState.value = iterationState.value.let {
            if (it.isFirstPage) return
            SimplePagination(
                it.page - 1,
                it.size
            )
        }
    }

    /**
     * Reloads the current page, refreshing the data.
     */
    fun reload() {
        iterationState.value = iterationState.value.let {
            SimplePagination(it.page, it.size)
        }
    }
}

/**
 * Composable function for paginated data displaying in a Compose UI.
 *
 * @param T The type of paginated data.
 * @param preload Optional preloaded pagination result.
 * @param initialPage Initial page number.
 * @param size Number of items per page.
 * @param loader Suspended function that loads paginated data.
 * @param block Composable function that renders the UI with the loaded data.
 */
@Composable
internal fun <T> PagedComponent(
    preload: PaginationResult<T>?,
    initialPage: Int,
    size: Int,
    loader: suspend PagedComponentContext<T>.(Pagination) -> PaginationResult<T>,
    block: @Composable PagedComponentContext<T>.(PaginationResult<T>) -> Unit
) {
    val context = remember { PagedComponentContext(preload, initialPage, size) }

    LaunchedEffect(context.iterationState.value.page, context.iterationState.value.hashCode()) {
        context.dataState.value = loader(context, context.iterationState.value)
    }

    context.dataState.value ?.let {
        context.block(it)
    }
}

/**
 * Overloaded composable function for paginated components with preloaded data.
 *
 * @param T The type of paginated data.
 * @param preload Preloaded pagination result.
 * @param loader Suspended function that loads paginated data.
 * @param block Composable function that renders the UI with the loaded data.
 */
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

/**
 * Overloaded composable function for paginated components with pagination info.
 *
 * @param T The type of paginated data.
 * @param pageInfo Initial pagination information.
 * @param loader Suspended function that loads paginated data.
 * @param block Composable function that renders the UI with the loaded data.
 */
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

/**
 * Overloaded composable function for paginated components with an initial page.
 *
 * @param T The type of paginated data.
 * @param initialPage Initial page number.
 * @param size Number of items per page.
 * @param loader Suspended function that loads paginated data.
 * @param block Composable function that renders the UI with the loaded data.
 */
@Composable
fun <T> PagedComponent(
    initialPage: Int,
    size: Int,
    loader: suspend PagedComponentContext<T>.(Pagination) -> PaginationResult<T>,
    block: @Composable PagedComponentContext<T>.(PaginationResult<T>) -> Unit
) {
    PagedComponent(null, initialPage, size, loader, block)
}

/**
 * Overloaded composable function for paginated components with only a size parameter.
 *
 * @param T The type of paginated data.
 * @param size Number of items per page.
 * @param loader Suspended function that loads paginated data.
 * @param block Composable function that renders the UI with the loaded data.
 */
@Composable
fun <T> PagedComponent(
    size: Int,
    loader: suspend PagedComponentContext<T>.(Pagination) -> PaginationResult<T>,
    block: @Composable PagedComponentContext<T>.(PaginationResult<T>) -> Unit
) {
    PagedComponent(0, size, loader, block)
}
