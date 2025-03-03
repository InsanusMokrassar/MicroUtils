package dev.inmo.micro_utils.pagination.compose

import androidx.compose.runtime.*
import dev.inmo.micro_utils.pagination.*

/**
 * Context for managing infinite pagination in a Compose UI.
 *
 * @param T The type of the data being paginated.
 * @property iterationState Holds the current pagination state and iteration count.
 * @property dataState Stores the loaded data, initially null.
 * @constructor Internal constructor to initialize pagination.
 * @param page Initial page number.
 * @param size Number of items per page.
 */
class InfinityPagedComponentContext<T> internal constructor(
    page: Int,
    size: Int
) {
    internal val startPage = SimplePagination(page, size)
    internal val iterationState: MutableState<Pair<Int, Pagination?>> = mutableStateOf(0 to null)
    internal val dataState: MutableState<List<T>?> = mutableStateOf(null)
    internal var lastPageLoaded = false

    /**
     * Loads the next page of data. If the current page is the last one, the function returns early.
     */
    fun loadNext() {
        if (lastPageLoaded) return
        if (iterationState.value.second is SimplePagination) return // Data loading has been inited but not loaded yet

        iterationState.value = iterationState.value.let {
            if ((it.second as? PaginationResult<*>) ?.isLastPage == true) return
            (it.first + 1) to (it.second ?: startPage).nextPage()
        }
    }

    /**
     * Reloads the pagination from the first page, clearing previously loaded data.
     */
    fun reload() {
        dataState.value = null
        lastPageLoaded = false
        iterationState.value = iterationState.value.let {
            (it.first + 1) to null
        }
    }
}

/**
 * Composable function for managing an infinitely paged component.
 *
 * @param T The type of the paginated data.
 * @param page Initial page number.
 * @param size Number of items per page.
 * @param loader Suspended function that loads paginated data.
 * @param block Composable function that renders the UI with the loaded data.
 */
@Composable
internal fun <T> InfinityPagedComponent(
    page: Int,
    size: Int,
    loader: suspend InfinityPagedComponentContext<T>.(Pagination) -> PaginationResult<T>,
    block: @Composable InfinityPagedComponentContext<T>.(List<T>?) -> Unit
) {
    val context = remember { InfinityPagedComponentContext<T>(page, size) }

    LaunchedEffect(context.iterationState.value.first) {
        val paginationResult = loader(context, context.iterationState.value.second ?: context.startPage)
        if (paginationResult.isLastPage) {
            context.lastPageLoaded = true
        }
        context.iterationState.value = context.iterationState.value.copy(second = paginationResult)
        context.dataState.value = (context.dataState.value ?: emptyList()) + paginationResult.results
    }

    context.dataState.value ?.let {
        context.block(context.dataState.value)
    }
}

/**
 * Overloaded composable function for an infinitely paged component.
 *
 * @param T The type of the paginated data.
 * @param pageInfo Initial pagination information.
 * @param loader Suspended function that loads paginated data.
 * @param block Composable function that renders the UI with the loaded data.
 */
@Composable
fun <T> InfinityPagedComponent(
    pageInfo: Pagination,
    loader: suspend InfinityPagedComponentContext<T>.(Pagination) -> PaginationResult<T>,
    block: @Composable InfinityPagedComponentContext<T>.(List<T>?) -> Unit
) {
    InfinityPagedComponent(
        pageInfo.page,
        pageInfo.size,
        loader,
        block
    )
}

/**
 * Overloaded composable function for an infinitely paged component.
 *
 * @param T The type of the paginated data.
 * @param size Number of items per page.
 * @param loader Suspended function that loads paginated data.
 * @param block Composable function that renders the UI with the loaded data.
 */
@Composable
fun <T> InfinityPagedComponent(
    size: Int,
    loader: suspend InfinityPagedComponentContext<T>.(Pagination) -> PaginationResult<T>,
    block: @Composable InfinityPagedComponentContext<T>.(List<T>?) -> Unit
) {
    InfinityPagedComponent(0, size, loader, block)
}
