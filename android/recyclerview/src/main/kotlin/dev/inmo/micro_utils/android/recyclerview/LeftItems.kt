package dev.inmo.micro_utils.android.recyclerview

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

fun RecyclerView.lastVisibleItemFlow(
    completingScope: CoroutineScope
): Flow<Int> {
    val lastVisibleElementFun: () -> Int = (layoutManager as? LinearLayoutManager) ?.let { it::findLastVisibleItemPosition } ?: error("Currently supported only linear layout manager")
    val lastVisibleFlow = MutableStateFlow(lastVisibleElementFun())
    addOnScrollListener(
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                lastVisibleFlow.value = lastVisibleElementFun()
            }
        }.also { scrollListener ->
            lastVisibleFlow.onCompletion {
                removeOnScrollListener(scrollListener)
            }.launchIn(completingScope)
        }
    )
    return lastVisibleFlow.asStateFlow()
}

inline fun Flow<Int>.mapLeftItems(
    crossinline countGetter: () -> Int
): Flow<Int> = map { countGetter() - it }

inline fun Flow<Int>.mapRequireFilling(
    minimalLeftItems: Int,
    crossinline countGetter: () -> Int
): Flow<Int> = mapLeftItems(countGetter).mapNotNull {
    if (it < minimalLeftItems) {
        it
    } else {
        null
    }
}

inline fun RecyclerView.mapRequireFilling(
    minimalLeftItems: Int,
    completingScope: CoroutineScope,
    crossinline countGetter: () -> Int
): Flow<Int> = lastVisibleItemFlow(completingScope).mapRequireFilling(minimalLeftItems, countGetter)
