package dev.inmo.micro_utils.android.recyclerview

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.*


abstract class RecyclerViewAdapter<T>: RecyclerView.Adapter<AbstractViewHolder<T>>() {
    protected abstract val data: List<T>

    private val _dataCountState by lazy {
        MutableStateFlow<Int>(data.size)
    }
    val dataCountState: StateFlow<Int> by lazy {
        _dataCountState.asStateFlow()
    }

    var emptyView: View? = null
        set(value) {
            field = value
            checkEmpty()
        }

    init {
        registerAdapterDataObserver(
            object : RecyclerView.AdapterDataObserver() {
                override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                    super.onItemRangeChanged(positionStart, itemCount)
                    _dataCountState.value = data.size
                    checkEmpty()
                }

                override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
                    super.onItemRangeChanged(positionStart, itemCount, payload)
                    _dataCountState.value = data.size
                    checkEmpty()
                }

                override fun onChanged() {
                    super.onChanged()
                    _dataCountState.value = data.size
                    checkEmpty()
                }

                override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                    super.onItemRangeRemoved(positionStart, itemCount)
                    _dataCountState.value = data.size
                    checkEmpty()
                }

                override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                    super.onItemRangeMoved(fromPosition, toPosition, itemCount)
                    _dataCountState.value = data.size
                    checkEmpty()
                }

                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    super.onItemRangeInserted(positionStart, itemCount)
                    _dataCountState.value = data.size
                    checkEmpty()
                }
            }
        )
        checkEmpty()
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: AbstractViewHolder<T>, position: Int) {
        holder.onBind(data[position])
    }

    private fun checkEmpty() {
        emptyView ?. let {
            if (dataCountState.value == 0) {
                it.visibility = View.VISIBLE
            } else {
                it.visibility = View.GONE
            }
        }
    }
}

fun <T> RecyclerViewAdapter(
    data: List<T>,
    onCreateViewHolder: (parent: ViewGroup, viewType: Int) -> AbstractViewHolder<T>
) = object : RecyclerViewAdapter<T>() {
    override val data: List<T> = data
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder<T> = onCreateViewHolder(parent, viewType)
}
