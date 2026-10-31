package dev.inmo.micro_utils.common

import org.w3c.dom.*

external class ResizeObserver(
    callback: (JsArray<ResizeObserverEntry>, ResizeObserver) -> Unit
): JsAny {
    fun observe(target: Element, options: JsAny = definedExternally)

    fun unobserve(target: Element)

    fun disconnect()
}

private fun createObserveOptions(jsBox: JsString?): JsAny = js("({box: jsBox})")

external interface ResizeObserverSize: JsAny {
    val blockSize: Float
    val inlineSize: Float
}

external interface ResizeObserverEntry: JsAny {
    val borderBoxSize: JsArray<ResizeObserverSize>
    val contentBoxSize: JsArray<ResizeObserverSize>
    val devicePixelContentBoxSize: JsArray<ResizeObserverSize>
    val contentRect: DOMRectReadOnly
    val target: Element
}

fun ResizeObserver.observe(target: Element, options: ResizeObserverObserveOptions) = observe(
    target,
    createObserveOptions(options.box?.name?.toJsString())
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
