package dev.inmo.micro_utils.common.compose

import org.jetbrains.compose.web.dom.AttrBuilderContext
import org.w3c.dom.Element

operator fun <T : Element> AttrBuilderContext<T>?.plus(
    other: AttrBuilderContext<T>?
) = when (this) {
    null -> other ?: {}
    else -> when (other) {
        null -> this ?: {}
        else -> {
            {
                invoke(this)
                other(this)
            }
        }
    }
}
