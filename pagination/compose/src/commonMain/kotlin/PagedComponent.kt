package dev.inmo.micro_utils.pagination.compose

import androidx.compose.runtime.*
import dev.inmo.micro_utils.coroutines.SpecialMutableStateFlow
import dev.inmo.micro_utils.coroutines.launchLoggingDropExceptions
import dev.inmo.micro_utils.pagination.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

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
    size: Int,
    private val scope: CoroutineScope,
    private val loader: suspend PagedComponentContext<T>.(Pagination) -> PaginationResult<T>
) {
    internal val startPage = SimplePagination(initialPage, size)
    internal val latestLoadedPage = SpecialMutableStateFlow<PaginationResult<T>?>(null)
    internal val dataState = SpecialMutableStateFlow<PaginationResult<T>?>(null)
    internal var loadingJob: Job? = null
    internal val loadingMutex = Mutex()

    private fun initLoadingJob(
        skipCheckerInLock: () -> Boolean,
        pageGetter: () -> Pagination
    ): Job {
        return scope.launchLoggingDropExceptions {
            loadingMutex.withLock {
                if (skipCheckerInLock()) return@launchLoggingDropExceptions
                loadingJob = loadingJob ?: scope.launchLoggingDropExceptions {
                    runCatching {
                        loader(pageGetter())
                    }.onSuccess {
                        latestLoadedPage.value = it
                        dataState.value = it
                    }
                    loadingMutex.withLock {
                        loadingJob = null
                    }
                }
                loadingJob
            } ?.join()
        }
    }

    /**
     * Loads the next page of data. If the last page is reached, this function returns early.
     */
    fun loadNext(): Job {
        return initLoadingJob(
            { latestLoadedPage.value ?.isLastPage == true }
        ) {
            latestLoadedPage.value ?.nextPage() ?: startPage
        }
    }

    /**
     * Loads the previous page of data if available.
     */
    fun loadPrevious(): Job {
        return initLoadingJob(
            { latestLoadedPage.value ?.isFirstPage == true }
        ) {
            latestLoadedPage.value ?.previousPage() ?: startPage
        }
    }

    /**
     * Reloads the current page, refreshing the data.
     */
    fun reload(): Job {
        return initLoadingJob(
            {
                latestLoadedPage.value = null
                true
            }
        ) {
            startPage
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
    initialPage: Int,
    size: Int,
    loader: suspend PagedComponentContext<T>.(Pagination) -> PaginationResult<T>,
    predefinedScope: CoroutineScope? = null,
    block: @Composable PagedComponentContext<T>.(PaginationResult<T>) -> Unit
) {
    val scope = predefinedScope ?: rememberCoroutineScope()
    val context = remember { PagedComponentContext<T>(initialPage, size, scope, loader) }
    remember {
        context.reload()
    }

    val pageState = context.dataState.collectAsState()
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
    predefinedScope: CoroutineScope? = null,
    block: @Composable PagedComponentContext<T>.(PaginationResult<T>) -> Unit
) {
    PagedComponent(
        pageInfo.page,
        pageInfo.size,
        loader,
        predefinedScope,
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
    predefinedScope: CoroutineScope? = null,
    block: @Composable PagedComponentContext<T>.(PaginationResult<T>) -> Unit
) {
    PagedComponent(0, size, loader, predefinedScope, block)
}
