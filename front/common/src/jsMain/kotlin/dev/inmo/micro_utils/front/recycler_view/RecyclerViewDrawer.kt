package dev.inmo.micro_utils.front.recycler_view

import org.w3c.dom.HTMLElement

interface RecyclerViewDrawer {
    fun drawBefore(what: HTMLElement, before: HTMLElement)
    fun drawAfter(what: HTMLElement, after: HTMLElement)
}
