package dev.inmo.micro_utils.coroutines

import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * [kotlinx.coroutines.flow.Flow]-based [android.view.ViewGroup.OnHierarchyChangeListener]
 *
 * @param recursive If set, any call of [onChildViewAdded] will check if child [View] is [ViewGroup] and subscribe to this
 * [ViewGroup] too
 * @param [_onChildViewAdded] Internal [MutableSharedFlow] which will be used to pass data to [onChildViewAdded] flow
 * @param [_onChildViewRemoved] Internal [MutableSharedFlow] which will be used to pass data to [onChildViewRemoved] flow
 */
class FlowOnHierarchyChangeListener(
    private val recursive: Boolean = false,
    private val _onChildViewAdded: MutableSharedFlow<Pair<View, View>> = MutableSharedFlow(extraBufferCapacity = Int.MAX_VALUE),
    private val _onChildViewRemoved: MutableSharedFlow<Pair<View, View>> = MutableSharedFlow(extraBufferCapacity = Int.MAX_VALUE)
) : ViewGroup.OnHierarchyChangeListener {
    val onChildViewAdded = _onChildViewAdded.asSharedFlow()
    val onChildViewRemoved = _onChildViewRemoved.asSharedFlow()

    /**
     * Will emit data into [onChildViewAdded] flow. If [recursive] is true and [child] is [ViewGroup] will also
     * subscribe to [child] hierarchy changes.
     *
     * Due to the fact that this method is not suspendable, [FlowOnHierarchyChangeListener] will use
     * [MutableSharedFlow.tryEmit] to send data into [_onChildViewAdded]. That is why its default extraBufferCapacity is
     * [Int.MAX_VALUE]
     */
    override fun onChildViewAdded(parent: View, child: View) {
        _onChildViewAdded.tryEmit(parent to child)

        if (recursive && child is ViewGroup) {
            child.setOnHierarchyChangeListener(this)
        }
    }

    /**
     * Just emit data into [onChildViewRemoved]
     *
     * Due to the fact that this method is not suspendable, [FlowOnHierarchyChangeListener] will use
     * [MutableSharedFlow.tryEmit] to send data into [_onChildViewRemoved]. That is why its default extraBufferCapacity is
     * [Int.MAX_VALUE]
     */
    override fun onChildViewRemoved(parent: View, child: View) {
        _onChildViewRemoved.tryEmit(parent to child)
    }
}
