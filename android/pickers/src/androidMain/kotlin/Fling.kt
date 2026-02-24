package dev.inmo.micro_utils.android.pickers

import androidx.compose.animation.core.*

/**
 * Performs a fling animation with an optional target adjustment.
 * If [adjustTarget] is provided, animates to the adjusted target. Otherwise, performs a decay animation.
 *
 * @param initialVelocity The initial velocity of the fling
 * @param animationSpec The decay animation specification
 * @param adjustTarget Optional function to adjust the target value based on the calculated target
 * @param block Optional block to be executed during the animation
 * @return The result of the animation
 */
internal suspend fun Animatable<Float, AnimationVector1D>.fling(
    initialVelocity: Float,
    animationSpec: DecayAnimationSpec<Float>,
    adjustTarget: ((Float) -> Float)?,
    block: (Animatable<Float, AnimationVector1D>.() -> Unit)? = null,
): AnimationResult<Float, AnimationVector1D> {
    val targetValue = animationSpec.calculateTargetValue(value, initialVelocity)
    val adjustedTarget = adjustTarget?.invoke(targetValue)

    return if (adjustedTarget != null) {
        animateTo(
            targetValue = adjustedTarget,
            initialVelocity = initialVelocity,
            block = block
        )
    } else {
        animateDecay(
            initialVelocity = initialVelocity,
            animationSpec = animationSpec,
            block = block,
        )
    }
}