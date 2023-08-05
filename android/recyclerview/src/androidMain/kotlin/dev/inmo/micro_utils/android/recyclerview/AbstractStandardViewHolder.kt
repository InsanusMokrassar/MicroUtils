package dev.inmo.micro_utils.android.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class AbstractStandardViewHolder<T>(
    inflater: LayoutInflater,
    container: ViewGroup?,
    viewId: Int,
    onViewInflated: ((View) -> Unit)? = null
) : AbstractViewHolder<T>(
    inflater.inflate(viewId, container, false).also {
        onViewInflated ?.invoke(it)
    }
) {
    constructor(
        container: ViewGroup,
        viewId: Int,
        onViewInflated: ((View) -> Unit)? = null
    ) : this(LayoutInflater.from(container.context), container, viewId, onViewInflated)
}
