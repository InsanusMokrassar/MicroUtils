package dev.inmo.micro_utils.android.recyclerview

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


abstract class RecyclerViewAdapter<T>: RecyclerView.Adapter<AbstractViewHolder<T>>() {
    protected abstract val data: List<T>

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
                    checkEmpty()
                }

                override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
                    super.onItemRangeChanged(positionStart, itemCount, payload)
                    checkEmpty()
                }

                override fun onChanged() {
                    super.onChanged()
                    checkEmpty()
                }

                override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                    super.onItemRangeRemoved(positionStart, itemCount)
                    checkEmpty()
                }

                override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                    super.onItemRangeMoved(fromPosition, toPosition, itemCount)
                    checkEmpty()
                }

                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    super.onItemRangeInserted(positionStart, itemCount)
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
            if (data.isEmpty()) {
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
