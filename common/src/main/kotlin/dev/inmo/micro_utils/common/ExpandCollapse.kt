package dev.inmo.micro_utils.common

import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation

@PreviewFeature
fun View.expand(
    duration: Long = 500,
    targetWidth: Int = ViewGroup.LayoutParams.MATCH_PARENT,
    targetHeight: Int = ViewGroup.LayoutParams.WRAP_CONTENT
) {
    measure(targetWidth, targetHeight)
    val measuredHeight: Int = measuredHeight
    layoutParams.height = 0
    visibility = View.VISIBLE
    val a: Animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            super.applyTransformation(interpolatedTime, t)
            layoutParams.height = if (interpolatedTime == 1f) targetHeight else (measuredHeight * interpolatedTime).toInt()
            requestLayout()
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }

    a.duration = duration

    startAnimation(a)
}

@PreviewFeature
fun View.collapse(duration: Long = 500) {
    val initialHeight: Int = measuredHeight
    val a: Animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            if (interpolatedTime == 1f) {
                visibility = View.GONE
            } else {
                layoutParams.height = initialHeight - (initialHeight * interpolatedTime).toInt()
                requestLayout()
            }
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }

    a.duration = duration

    startAnimation(a)
}

@PreviewFeature
inline val View.isCollapsed
    get() = visibility == View.GONE

@PreviewFeature
inline val View.isExpanded
    get() = !isCollapsed

/**
 * @return true in case of expanding
 */
@PreviewFeature
fun View.toggleExpandState(duration: Long = 500): Boolean = if (isCollapsed) {
    expand(duration)
    true
} else {
    collapse(duration)
    false
}
