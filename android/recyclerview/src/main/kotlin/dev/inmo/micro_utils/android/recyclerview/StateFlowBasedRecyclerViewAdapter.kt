package dev.inmo.micro_utils.android.recyclerview

import dev.inmo.micro_utils.common.Diff
import dev.inmo.micro_utils.common.PreviewFeature
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@PreviewFeature("This feature in preview state and may contains different bugs. " +
                "Besides, this feature can be changed in future in non-compatible way")
abstract class StateFlowBasedRecyclerViewAdapter<T>(
    listeningScope: CoroutineScope,
    dataState: StateFlow<List<T>>
) : RecyclerViewAdapter<T>() {
    override var data: List<T> = emptyList()

    init {
        dataState.onEach {
            val diffForRemoves = Diff(data, it)
            val removedIndexes = diffForRemoves.removed.map { it.index }
            val leftRemove = removedIndexes.toMutableList()
            data = data.filterIndexed { i, _ ->
                if (i in leftRemove) {
                    leftRemove.remove(i)
                    true
                } else {
                    false
                }
            }
            withContext(Dispatchers.Main) {
                removedIndexes.sortedDescending().forEach {
                    notifyItemRemoved(it)
                }
            }
            val diffAddsAndReplaces = Diff(data, it)
            data = it
            withContext(Dispatchers.Main) {
                diffAddsAndReplaces.replaced.forEach { (from, to) ->
                    notifyItemMoved(from.index, to.index)
                }
                diffAddsAndReplaces.added.forEach {
                    notifyItemInserted(it.index)
                }
            }
        }.launchIn(listeningScope)
    }
}
