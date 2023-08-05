package dev.inmo.micro_utils.common

import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation

private fun View.performExpand(
    duration: Long = 500,
    targetWidth: Int = ViewGroup.LayoutParams.MATCH_PARENT,
    targetHeight: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    onMeasured: View.() -> Unit,
    onPerformAnimation: View.(interpolatedTime: Float, t: Transformation?) -> Unit
) {
    measure(targetWidth, targetHeight)
    onMeasured()
    show()
    val a: Animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            super.applyTransformation(interpolatedTime, t)
            onPerformAnimation(interpolatedTime, t)
            requestLayout()
        }

        override fun willChangeBounds(): Boolean = true
    }

    a.duration = duration
    startAnimation(a)
}

private fun View.performCollapse(
    duration: Long = 500,
    onPerformAnimation: View.(interpolatedTime: Float, t: Transformation?) -> Unit
) {
    val a: Animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            if (interpolatedTime == 1f) {
                gone()
            } else {
                onPerformAnimation(interpolatedTime, t)
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
fun View.expand(
    duration: Long = 500,
    targetWidth: Int = ViewGroup.LayoutParams.MATCH_PARENT,
    targetHeight: Int = ViewGroup.LayoutParams.WRAP_CONTENT
) {
    var measuredHeight = 0
    performExpand(
        duration,
        targetWidth,
        targetHeight,
        {
            measuredHeight = this.measuredHeight
        }
    ) { interpolatedTime, _ ->
        layoutParams.height = if (interpolatedTime == 1f) targetHeight else (measuredHeight * interpolatedTime).toInt()
    }
}

@PreviewFeature
fun View.expandHorizontally(
    duration: Long = 500,
    targetWidth: Int = ViewGroup.LayoutParams.MATCH_PARENT,
    targetHeight: Int = ViewGroup.LayoutParams.WRAP_CONTENT
) {
    var measuredWidth = 0
    performExpand(
        duration,
        targetWidth,
        targetHeight,
        {
            measuredWidth = this.measuredWidth
        }
    ) { interpolatedTime, _ ->
        layoutParams.width = if (interpolatedTime == 1f) targetWidth else (measuredWidth * interpolatedTime).toInt()
    }
}

@PreviewFeature
fun View.collapse(duration: Long = 500) {
    val initialHeight: Int = measuredHeight
    performCollapse(duration) { interpolatedTime, _ ->
        layoutParams.height = initialHeight - (initialHeight * interpolatedTime).toInt()
    }
}

@PreviewFeature
fun View.collapseHorizontally(duration: Long = 500) {
    val initialWidth: Int = measuredWidth
    performCollapse(duration) { interpolatedTime, _ ->
        layoutParams.width = initialWidth - (initialWidth * interpolatedTime).toInt()
    }
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

/**
 * @return true in case of expanding
 */
@PreviewFeature
fun View.toggleExpandHorizontallyState(duration: Long = 500): Boolean = if (isCollapsed) {
    expandHorizontally(duration)
    true
} else {
    collapseHorizontally(duration)
    false
}
