package dev.inmo.micro_utils.selector

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

/**
 * @return Returned [SharedFlow] will emit true when [element] has been selected in [this] [Selector] and will emit
 * false when this [element] was deselected
 *
 * @see [Selector]
 * @see [Selector.itemSelected]
 * @see [Selector.itemUnselected]
 */
fun <T> Selector<T>.itemSelectionFlow(element: T, scope: CoroutineScope): SharedFlow<Boolean> = MutableSharedFlow<Boolean>().apply {
    itemSelected.onEach { if (it == element) emit(true) }.launchIn(scope)
    itemUnselected.onEach { if (it == element) emit(false) }.launchIn(scope)
}.asSharedFlow()
