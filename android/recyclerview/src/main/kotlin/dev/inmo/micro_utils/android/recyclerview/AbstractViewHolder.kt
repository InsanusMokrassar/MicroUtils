package dev.inmo.micro_utils.android.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class AbstractViewHolder<in T>(
    view: View
) : RecyclerView.ViewHolder(view) {
    abstract fun onBind(item: T)
}
