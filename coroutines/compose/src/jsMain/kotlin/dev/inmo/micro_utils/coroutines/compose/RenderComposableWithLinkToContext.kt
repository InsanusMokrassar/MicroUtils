package dev.inmo.micro_utils.coroutines.compose

import androidx.compose.runtime.*
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
