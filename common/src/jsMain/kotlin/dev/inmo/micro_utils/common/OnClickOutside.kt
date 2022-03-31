package dev.inmo.micro_utils.common

import kotlinx.browser.document
import org.w3c.dom.*
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventListener

fun Element.onActionOutside(type: String, options: dynamic = null, callback: (Event) -> Unit): EventListener {
    lateinit var observer: MutationObserver
    val listener = EventListener {
        val elementsToCheck = mutableListOf<Element>(this@onActionOutside)
        while (it.target != this@onActionOutside && elementsToCheck.isNotEmpty()) {
            val childrenGettingElement = elementsToCheck.removeFirst()
            for (i in 0 until childrenGettingElement.childElementCount) {
                elementsToCheck.add(childrenGettingElement.children[i] ?: continue)
            }
        }
        if (elementsToCheck.isEmpty()) {
            callback(it)
        }
    }
    if (options == null) {
        document.addEventListener(type, listener)
    } else {
        document.addEventListener(type, listener, options)
    }
    observer = onRemoved {
        if (options == null) {
            document.removeEventListener(type, listener)
        } else {
            document.removeEventListener(type, listener, options)
        }
        observer.disconnect()
    }
    return listener
}

fun Element.onClickOutside(options: dynamic = null, callback: (Event) -> Unit) = onActionOutside("click", options, callback)
