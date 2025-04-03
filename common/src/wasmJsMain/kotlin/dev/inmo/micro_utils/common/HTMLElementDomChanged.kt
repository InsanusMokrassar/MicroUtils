package dev.inmo.micro_utils.common

import kotlinx.browser.document
import org.w3c.dom.*

private fun createMutationObserverInit(childList: Boolean, subtree: Boolean): JsAny = js("({childList, subtree})")

fun Node.onRemoved(block: () -> Unit): MutationObserver {
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

    observer.observe(document, createMutationObserverInit(childList = true, subtree = true).unsafeCast())
    return observer
}

fun Element.onVisibilityChanged(block: IntersectionObserverEntry.(Float, IntersectionObserver) -> Unit): IntersectionObserver {
    var previousIntersectionRatio = -1f
    val observer = IntersectionObserver { entries, observer ->
        entries.toArray().forEach {
            if (previousIntersectionRatio.toDouble() != it.intersectionRatio.toDouble()) {
                previousIntersectionRatio = it.intersectionRatio.toDouble().toFloat()
                it.block(previousIntersectionRatio, observer)
            }
        }
    }

    observer.observe(this)
    return observer
}

fun Element.onVisible(block: Element.(IntersectionObserver) -> Unit) {
    var previous = -1f
    onVisibilityChanged { intersectionRatio, observer ->
        if (previous != intersectionRatio) {
            if (intersectionRatio > 0 && previous == 0f) {
                block(observer)
            }
            previous = intersectionRatio
        }
    }
}

fun Element.onInvisible(block: Element.(IntersectionObserver) -> Unit): IntersectionObserver {
    var previous = -1f
    return onVisibilityChanged { intersectionRatio, observer ->
        if (previous != intersectionRatio) {
            if (intersectionRatio == 0f && previous != 0f) {
                block(observer)
            }
            previous = intersectionRatio
        }
    }
}
