package dev.inmo.micro_utils.common

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment

fun findViewsByTag(viewGroup: ViewGroup, tag: Any?): List<View> {
    return viewGroup.children.flatMap {
        findViewsByTag(it, tag)
    }.toList()
}

fun findViewsByTag(viewGroup: ViewGroup, key: Int, tag: Any?): List<View> {
    return viewGroup.children.flatMap {
        findViewsByTag(it, key, tag)
    }.toList()
}

fun findViewsByTag(view: View, tag: Any?): List<View> {
    val result = mutableListOf<View>()
    if (view.tag == tag) {
        result.add(view)
    }
    if (view is ViewGroup) {
        result.addAll(findViewsByTag(view, tag))
    }
    return result.toList()
}

fun findViewsByTag(view: View, key: Int, tag: Any?): List<View> {
    val result = mutableListOf<View>()
    if (view.getTag(key) == tag) {
        result.add(view)
    }
    if (view is ViewGroup) {
        result.addAll(findViewsByTag(view, key, tag))
    }
    return result.toList()
}

fun Activity.findViewsByTag(tag: Any?) = rootView ?.let {
    findViewsByTag(it, tag)
}

fun Activity.findViewsByTag(key: Int, tag: Any?) = rootView ?.let {
    findViewsByTag(it, key, tag)
}

fun Fragment.findViewsByTag(tag: Any?) = view ?.let {
    findViewsByTag(it, tag)
}

fun Fragment.findViewsByTag(key: Int, tag: Any?) = view ?.let {
    findViewsByTag(it, key, tag)
}

fun Fragment.findViewsByTagInActivity(tag: Any?) = activity ?.findViewsByTag(tag)

fun Fragment.findViewsByTagInActivity(key: Int, tag: Any?) = activity ?.findViewsByTag(key, tag)
