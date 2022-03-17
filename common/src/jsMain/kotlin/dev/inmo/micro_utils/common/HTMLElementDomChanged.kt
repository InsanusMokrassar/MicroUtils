package dev.inmo.micro_utils.common

import kotlinx.browser.document
import org.w3c.dom.*

fun Node.onRemoved(block: () -> Unit) {
    lateinit var observer: MutationObserver

    observer = MutationObserver { _, _ ->
        fun checkIfRemoved(node: Node): Boolean {
            return node.parentNode != document && (node.parentNode ?.let { checkIfRemoved(it) } ?: true)
        }

        if (checkIfRemoved(this)) {
            observer.disconnect()
            block()
        }
    }

    observer.observe(document, MutationObserverInit(childList = true, subtree = true))
}
