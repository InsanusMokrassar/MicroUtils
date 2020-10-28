package dev.inmo.micro_utils.front.recycler_view

import dev.inmo.micro_utils.common.isOnScreen
import kotlinx.browser.document
import org.w3c.dom.HTMLDivElement

abstract class RecyclerViewAdapter<T>(
    protected val data: List<T>,
    protected val drawer: RecyclerViewDrawer,
    private val visibleOutsideOfViewportElements: Int = 1
) {
    val element: HTMLDivElement = document.createElement("div") as HTMLDivElement
    protected val itemsToElements = mutableMapOf<T, RecyclerViewElement>()

    abstract fun createViewFactory(element: T): RecyclerViewElement

    fun updateVisibleElements() {
        var firstVisibleElementIndex = -1
        var lastVisibleElementIndex = -1

        for ((i, item) in data.withIndex()) {
            itemsToElements[item] ?.let {
                firstVisibleElementIndex = when {
                    firstVisibleElementIndex == -1 -> i
                    it.element.isOnScreen -> minOf(firstVisibleElementIndex, i)
                    else -> firstVisibleElementIndex
                }
                lastVisibleElementIndex = when {
                    lastVisibleElementIndex == -1 -> i
                    it.element.isOnScreen -> maxOf(lastVisibleElementIndex, i)
                    else -> lastVisibleElementIndex
                }
            }
        }


    }
}
