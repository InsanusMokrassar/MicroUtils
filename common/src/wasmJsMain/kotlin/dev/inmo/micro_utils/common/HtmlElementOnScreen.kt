package dev.inmo.micro_utils.common

import kotlinx.browser.window
import org.w3c.dom.DOMRect
import org.w3c.dom.Element

val DOMRect.isOnScreenByLeftEdge: Boolean
    get() = left >= 0 && left <= window.innerWidth
inline val Element.isOnScreenByLeftEdge
    get() = getBoundingClientRect().isOnScreenByLeftEdge

val DOMRect.isOnScreenByRightEdge: Boolean
    get() = right >= 0 && right <= window.innerWidth
inline val Element.isOnScreenByRightEdge
    get() = getBoundingClientRect().isOnScreenByRightEdge

internal val DOMRect.isOnScreenHorizontally: Boolean
    get() = isOnScreenByLeftEdge || isOnScreenByRightEdge


val DOMRect.isOnScreenByTopEdge: Boolean
    get() = top >= 0 && top <= window.innerHeight
inline val Element.isOnScreenByTopEdge
    get() = getBoundingClientRect().isOnScreenByTopEdge

val DOMRect.isOnScreenByBottomEdge: Boolean
    get() = bottom >= 0 && bottom <= window.innerHeight
inline val Element.isOnScreenByBottomEdge
    get() = getBoundingClientRect().isOnScreenByBottomEdge

internal val DOMRect.isOnScreenVertically: Boolean
    get() = isOnScreenByLeftEdge || isOnScreenByRightEdge


val DOMRect.isOnScreenFully: Boolean
    get() = isOnScreenByLeftEdge && isOnScreenByTopEdge && isOnScreenByRightEdge && isOnScreenByBottomEdge
val Element.isOnScreenFully: Boolean
    get() = getBoundingClientRect().isOnScreenFully

val DOMRect.isOnScreen: Boolean
    get() = isOnScreenFully || (isOnScreenHorizontally && isOnScreenVertically)
inline val Element.isOnScreen: Boolean
    get() = getBoundingClientRect().isOnScreen
