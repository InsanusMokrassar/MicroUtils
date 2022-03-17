package dev.inmo.micro_utils.common.compose

import androidx.compose.runtime.*
import org.jetbrains.compose.web.dom.DOMScope
import org.w3c.dom.Element

fun <TElement : Element> renderComposableAndLinkToRoot(
    root: TElement,
    monotonicFrameClock: MonotonicFrameClock = DefaultMonotonicFrameClock,
    content: @Composable DOMScope<TElement>.() -> Unit
): Composition = org.jetbrains.compose.web.renderComposable(root, monotonicFrameClock, content).apply {
    linkWithElement(root)
}
