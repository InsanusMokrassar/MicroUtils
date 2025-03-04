package dev.inmo.micro_utils.pagination.compose

import androidx.compose.runtime.*
import dev.inmo.micro_utils.coroutines.SpecialMutableStateFlow
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
    internal val currentlyLoadingPage = SpecialMutableStateFlow<Pagination?>(startPage)
    internal val latestLoadedPage = SpecialMutableStateFlow<PaginationResult<T>?>(null)
    internal val dataState = SpecialMutableStateFlow<List<T>?>(null)

    /**
     * Loads the next page of data. If the current page is the last one, the function returns early.
     */
    fun loadNext() {
        if (latestLoadedPage.value ?.isLastPage == true) return
        if (currentlyLoadingPage.value != null) return // Data loading has been inited but not loaded yet

        currentlyLoadingPage.value = latestLoadedPage.value ?.nextPage() ?: startPage
    }

    /**
     * Reloads the pagination from the first page, clearing previously loaded data.
     */
    fun reload() {
        latestLoadedPage.value = null
        currentlyLoadingPage.value = null
        loadNext()
    }
}

/**
 * Composable function for managing an infinitely paged component.
 *
 * @param T The type of the paginated data.
 * @param page Initial page number.
 * @param size Number of items per page.
 * @param loader Suspended function that loads paginated data.
 * @param block Composable function that renders the UI with the loaded data. When data is in loading state, block will
 * receive null as `it` parameter
 */
@Composable
internal fun <T> InfinityPagedComponent(
    page: Int,
    size: Int,
    loader: suspend InfinityPagedComponentContext<T>.(Pagination) -> PaginationResult<T>,
    block: @Composable InfinityPagedComponentContext<T>.(List<T>?) -> Unit
) {
    val context = remember { InfinityPagedComponentContext<T>(page, size) }

    val currentlyLoadingState = context.currentlyLoadingPage.collectAsState()
    LaunchedEffect(currentlyLoadingState.value) {
        val paginationResult = loader(context, currentlyLoadingState.value ?: return@LaunchedEffect)
        context.latestLoadedPage.value = paginationResult
        context.currentlyLoadingPage.value = null

        context.dataState.value = (context.dataState.value ?: emptyList()) + paginationResult.results
    }

    val dataState = context.dataState.collectAsState()
    context.block(dataState.value)
}

/**
 * Overloaded composable function for an infinitely paged component.
 *
 * @param T The type of the paginated data.
 * @param pageInfo Initial pagination information.
 * @param loader Suspended function that loads paginated data.
 * @param block Composable function that renders the UI with the loaded data. When data is in loading state, block will
 * receive null as `it` parameter
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
 * @param block Composable function that renders the UI with the loaded data. When data is in loading state, block will
 * receive null as `it` parameter
 */
@Composable
fun <T> InfinityPagedComponent(
    size: Int,
    loader: suspend InfinityPagedComponentContext<T>.(Pagination) -> PaginationResult<T>,
    block: @Composable InfinityPagedComponentContext<T>.(List<T>?) -> Unit
) {
    InfinityPagedComponent(0, size, loader, block)
}
