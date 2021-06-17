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
            val diff = Diff(data, it)
            data = it
            withContext(Dispatchers.Main) {
                diff.removed.forEach {
                    notifyItemRemoved(it.index)
                }
                diff.replaced.forEach { (from, to) ->
                    notifyItemMoved(from.index, to.index)
                }
                diff.added.forEach {
                    notifyItemInserted(it.index)
                }
            }
        }.launchIn(listeningScope)
    }
}
