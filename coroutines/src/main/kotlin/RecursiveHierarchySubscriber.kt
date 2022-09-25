package dev.inmo.micro_utils.coroutines

import android.view.ViewGroup
import android.view.ViewGroup.OnHierarchyChangeListener

/**
 * Use [ViewGroup.setOnHierarchyChangeListener] recursively for all available [ViewGroup]s starting with [this].
 * This extension DO NOT guarantee that recursive subscription will happen after this method call
 */
fun ViewGroup.setOnHierarchyChangeListenerRecursively(
    listener: OnHierarchyChangeListener
) {
    setOnHierarchyChangeListener(listener)
    (0 until childCount).forEach {
        (getChildAt(it) as? ViewGroup) ?.setOnHierarchyChangeListener(listener)
    }
}
