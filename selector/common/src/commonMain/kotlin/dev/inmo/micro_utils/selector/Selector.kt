package dev.inmo.micro_utils.selector

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.sync.Mutex

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

class SingleSelector<T>(
    selectedItem: T? = null,
    useMutex: Boolean = true
) : Selector<T> {
    var selectedItem: T? = selectedItem
        private set
    override val selectedItems: List<T>
        get() = selectedItem ?.let { listOf(it) } ?: emptyList()

    private val _itemSelected = MutableSharedFlow<T>()
    override val itemSelected: SharedFlow<T> = _itemSelected.asSharedFlow()
    private val _itemUnselected = MutableSharedFlow<T>()
    override val itemUnselected: SharedFlow<T> = _itemUnselected.asSharedFlow()

    private val mutex = if (useMutex) {
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

class MultipleSelector<T>(
    selectedItems: List<T>,
    useMutex: Boolean = true
) : Selector<T> {
    private val _selectedItems: MutableList<T> = selectedItems.toMutableList()
    override val selectedItems: List<T> = _selectedItems

    private val _itemSelected = MutableSharedFlow<T>()
    override val itemSelected: SharedFlow<T> = _itemSelected.asSharedFlow()
    private val _itemUnselected = MutableSharedFlow<T>()
    override val itemUnselected: SharedFlow<T> = _itemUnselected.asSharedFlow()

    private val mutex = if (useMutex) {
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
