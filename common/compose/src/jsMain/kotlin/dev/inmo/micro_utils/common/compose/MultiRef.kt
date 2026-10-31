package dev.inmo.micro_utils.common.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.DisposableEffectResult
import androidx.compose.runtime.DisposableEffectScope
import org.jetbrains.compose.web.attributes.AttrsScope
import org.jetbrains.compose.web.dom.ElementScope
import org.w3c.dom.Element

/**
 * This function must be called in the context of your tag content. It works like default [AttrsScope.ref],
 * but able to be used several times. Uses [DisposableEffect] under the hood
 */
@Composable
fun <T : Element> ElementScope<T>.ref(
    block: DisposableEffectScope.(T) -> DisposableEffectResult
) {
    DisposableEffect(0) {
        block(scopeElement)
    }
}
