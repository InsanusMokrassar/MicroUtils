package dev.inmo.micro_utils.coroutines.compose

import androidx.compose.runtime.*
import dev.inmo.micro_utils.common.compose.linkWithElement
import kotlinx.coroutines.*
import org.jetbrains.compose.web.dom.DOMScope
import org.w3c.dom.Element

suspend fun <TElement : Element> renderComposableAndLinkToContext(
    root: TElement,
    monotonicFrameClock: MonotonicFrameClock = DefaultMonotonicFrameClock,
    content: @Composable DOMScope<TElement>.() -> Unit
): Composition = org.jetbrains.compose.web.renderComposable(root, monotonicFrameClock, content).apply {
    linkWithContext(
        currentCoroutineContext()
    )
}

suspend fun <TElement : Element> renderComposableAndLinkToContextAndRoot(
    root: TElement,
    monotonicFrameClock: MonotonicFrameClock = DefaultMonotonicFrameClock,
    content: @Composable DOMScope<TElement>.() -> Unit
): Composition = org.jetbrains.compose.web.renderComposable(root, monotonicFrameClock, content).apply {
    linkWithContext(currentCoroutineContext())
    linkWithElement(root)
}
