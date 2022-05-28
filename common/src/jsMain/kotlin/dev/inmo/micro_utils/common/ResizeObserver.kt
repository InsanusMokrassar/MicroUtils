package dev.inmo.micro_utils.common

import org.w3c.dom.*
import kotlin.js.Json
import kotlin.js.json

external class ResizeObserver(
    callback: (Array<ResizeObserverEntry>, ResizeObserver) -> Unit
) {
    fun observe(target: Element, options: Json = definedExternally)

    fun unobserve(target: Element)

    fun disconnect()
}

external interface ResizeObserverSize {
    val blockSize: Float
    val inlineSize: Float
}

external interface ResizeObserverEntry {
    val borderBoxSize: Array<ResizeObserverSize>
    val contentBoxSize: Array<ResizeObserverSize>
    val devicePixelContentBoxSize: Array<ResizeObserverSize>
    val contentRect: DOMRectReadOnly
    val target: Element
}

fun ResizeObserver.observe(target: Element, options: ResizeObserverObserveOptions) = observe(
    target,
    json(
        "box" to options.box ?.name
    )
)

class ResizeObserverObserveOptions(
    val box: Box? = null
) {
    sealed interface Box {
        val name: String

        object Content : Box {
            override val name: String
                get() = "content-box"
        }

        object Border : Box {
            override val name: String
                get() = "border-box"
        }

        object DevicePixelContent : Box {
            override val name: String
                get() = "device-pixel-content-box"
        }
    }
}
