package dev.inmo.micro_utils.common.compose

import androidx.compose.runtime.DisposableEffectResult

class DefaultDisposableEffectResult(
    private val onDispose: () -> Unit
) : DisposableEffectResult {
    override fun dispose() {
        onDispose()
    }

    companion object {
        val DoNothing = DefaultDisposableEffectResult {}
    }
}

