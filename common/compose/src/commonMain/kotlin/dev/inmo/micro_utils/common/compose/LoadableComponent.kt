package dev.inmo.micro_utils.common.compose

import androidx.compose.runtime.*
import dev.inmo.micro_utils.common.Optional
import dev.inmo.micro_utils.common.dataOrThrow
import dev.inmo.micro_utils.common.optional

class LoadableComponentContext<T> internal constructor(
    presetOptional: Optional<T>,
) {
    internal val iterationState: MutableState<Int> = mutableStateOf(0)

    internal var dataOptional: Optional<T> = if (presetOptional.dataPresented) presetOptional else Optional.absent()
        private set
    internal val dataState: MutableState<Optional<T>> = mutableStateOf(dataOptional)

    fun reload() {
        iterationState.value++
    }
}

/**
 * Showing data with ability to reload data
 *
 * [block] will be shown when [loader] will complete loading. If you want to reload data, just call
 * [LoadableComponentContext.reload]
 */
@Composable
fun <T> LoadableComponent(
    preload: Optional<T>,
    loader: suspend LoadableComponentContext<T>.() -> T,
    block: @Composable LoadableComponentContext<T>.(T) -> Unit
) {
    val context = remember { LoadableComponentContext(preload) }

    LaunchedEffect(context.iterationState.value) {
        context.dataState.value = loader(context).optional
    }

    context.dataState.let {
        if (it.value.dataPresented) {
            context.block(it.value.dataOrThrow(IllegalStateException("Data must be presented, but optional has been changed by some way")))
        }
    }
}

/**
 * Showing data with ability to reload data
 *
 * [block] will be shown when [loader] will complete loading. If you want to reload data, just call
 * [LoadableComponentContext.reload]
 */
@Composable
fun <T> LoadableComponent(
    preload: T,
    loader: suspend LoadableComponentContext<T>.() -> T,
    block: @Composable LoadableComponentContext<T>.(T) -> Unit
) {
    LoadableComponent(preload.optional, loader, block)
}

/**
 * Showing data with ability to reload data
 *
 * [block] will be shown when [loader] will complete loading. If you want to reload data, just call
 * [LoadableComponentContext.reload]
 */
@Composable
fun <T> LoadableComponent(
    loader: suspend LoadableComponentContext<T>.() -> T,
    block: @Composable LoadableComponentContext<T>.(T) -> Unit
) {
    LoadableComponent(Optional.absent(), loader, block)
}
