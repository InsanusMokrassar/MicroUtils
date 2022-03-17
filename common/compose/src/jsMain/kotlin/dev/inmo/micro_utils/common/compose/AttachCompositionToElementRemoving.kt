package dev.inmo.micro_utils.common.compose

import androidx.compose.runtime.Composition
import dev.inmo.micro_utils.common.onRemoved
import org.w3c.dom.Element

fun Composition.linkWithElement(element: Element) {
    element.onRemoved { dispose() }
}
