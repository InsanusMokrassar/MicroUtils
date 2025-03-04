package dev.inmo.micro_utils.pagination.compose

import androidx.compose.runtime.*
import dev.inmo.micro_utils.coroutines.SpecialMutableStateFlow
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
    initialPage: Int,
    size: Int
) {
    internal val startPage = SimplePagination(initialPage, size)
    internal val currentlyLoadingPageState = SpecialMutableStateFlow<Pagination?>(startPage)
    internal val latestLoadedPage = SpecialMutableStateFlow<PaginationResult<T>?>(null)

    /**
     * Loads the next page of data. If the last page is reached, this function returns early.
     */
    fun loadNext() {
        when {
            currentlyLoadingPageState.value != null -> return
            latestLoadedPage.value ?.isLastPage == true -> return
            else -> currentlyLoadingPageState.value = (latestLoadedPage.value ?.nextPage()) ?: startPage
        }
    }

    /**
     * Loads the previous page of data if available.
     */
    fun loadPrevious() {
        when {
            currentlyLoadingPageState.value != null -> return
            latestLoadedPage.value ?.isFirstPage == true -> return
            else -> currentlyLoadingPageState.value = (latestLoadedPage.value ?.previousPage()) ?: startPage
        }
    }

    /**
     * Reloads the current page, refreshing the data.
     */
    fun reload() {
        currentlyLoadingPageState.value = latestLoadedPage.value
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
    initialPage: Int,
    size: Int,
    loader: suspend PagedComponentContext<T>.(Pagination) -> PaginationResult<T>,
    block: @Composable PagedComponentContext<T>.(PaginationResult<T>) -> Unit
) {
    val context = remember { PagedComponentContext<T>(initialPage, size) }

    val currentlyLoadingState = context.currentlyLoadingPageState.collectAsState()
    LaunchedEffect(currentlyLoadingState.value) {
        val paginationResult = loader(context, currentlyLoadingState.value ?: return@LaunchedEffect)
        context.latestLoadedPage.value = paginationResult
        context.currentlyLoadingPageState.value = null
    }

    val pageState = context.latestLoadedPage.collectAsState()
    pageState.value ?.let {
        context.block(it)
    }
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
        pageInfo.page,
        pageInfo.size,
        loader,
        block
    )
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
