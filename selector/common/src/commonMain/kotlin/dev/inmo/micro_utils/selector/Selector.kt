package dev.inmo.micro_utils.selector

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.sync.Mutex

/**
 * Unified interface which can be used in any system which require some selection functionality
 */
interface Selector<T> {
    val selectedItems: List<T>
    val itemSelected: SharedFlow<T>
    val itemUnselected: SharedFlow<T>

    suspend fun toggleSelection(element: T)
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun <T> Selector<T>.contains(element: T) = selectedItems.contains(element)
@Suppress("NOTHING_TO_INLINE")
inline fun <T> Selector<T>.nothingSelected(): Boolean = selectedItems.isEmpty()

/**
 * Realization of [Selector] with one or without selected element. This realization will always have empty
 * [selectedItems] when nothing selected and one element in [selectedItems] when something selected. Contains
 * [selectedItem] value for simple access to currently selected item.
 *
 * On calling of [toggleSelection] previous selection will be erased and [itemUnselected] will emit this element.
 *
 * @param safeChanges Set to false to disable using of [mutex] for synchronizing changes on [toggleSelection]
 */
class SingleSelector<T>(
    selectedItem: T? = null,
    safeChanges: Boolean = true
) : Selector<T> {
    var selectedItem: T? = selectedItem
        private set
    override val selectedItems: List<T>
        get() = selectedItem ?.let { listOf(it) } ?: emptyList()

    private val _itemSelected = MutableSharedFlow<T>()
    override val itemSelected: SharedFlow<T> = _itemSelected.asSharedFlow()
    private val _itemUnselected = MutableSharedFlow<T>()
    override val itemUnselected: SharedFlow<T> = _itemUnselected.asSharedFlow()

    private val mutex = if (safeChanges) {
        Mutex()
    } else {
        null
    }

    override suspend fun toggleSelection(element: T) {
        mutex ?.lock()
        if (selectedItem == element) {
            selectedItem = null
            _itemUnselected.emit(element)
        } else {
            val previouslySelected = selectedItem
            selectedItem = null
            if (previouslySelected != null) {
                _itemUnselected.emit(previouslySelected)
            }
            selectedItem = element
            _itemSelected.emit(element)
        }
        mutex ?.unlock()
    }
}

/**
 * Realization of [Selector] with multiple selected elements. On calling of [toggleSelection] this realization will select passed element OR deselect it if it is already in
 * [selectedItems]
 *
 * @param safeChanges Set to false to disable using of [mutex] for synchronizing changes on [toggleSelection]
 */
class MultipleSelector<T>(
    selectedItems: List<T> = emptyList(),
    safeChanges: Boolean = true
) : Selector<T> {
    private val _selectedItems: MutableList<T> = selectedItems.toMutableList()
    override val selectedItems: List<T> = _selectedItems

    private val _itemSelected = MutableSharedFlow<T>()
    override val itemSelected: SharedFlow<T> = _itemSelected.asSharedFlow()
    private val _itemUnselected = MutableSharedFlow<T>()
    override val itemUnselected: SharedFlow<T> = _itemUnselected.asSharedFlow()

    private val mutex = if (safeChanges) {
        Mutex()
    } else {
        null
    }

    override suspend fun toggleSelection(element: T) {
        mutex ?.lock()
        if (_selectedItems.remove(element)) {
            _itemUnselected.emit(element)
        } else {
            _selectedItems.add(element)
            _itemSelected.emit(element)
        }
        mutex ?.unlock()
    }
}

@Suppress("FunctionName", "NOTHING_TO_INLINE")
inline fun <T> Selector(
    multiple: Boolean,
    safeChanges: Boolean = true
): Selector<T> = if (multiple) {
    MultipleSelector(safeChanges = safeChanges)
} else {
    SingleSelector(safeChanges = safeChanges)
}
