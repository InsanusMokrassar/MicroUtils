package dev.inmo.micro_utils.front.recycler_view

import org.w3c.dom.HTMLElement

interface RecyclerViewElement {
    val element: HTMLElement
    suspend fun onOutsideOfViewport()
    suspend fun onInsideOfViewport()
}
