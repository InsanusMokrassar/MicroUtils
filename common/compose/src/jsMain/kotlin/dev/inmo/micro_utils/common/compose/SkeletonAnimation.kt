package dev.inmo.micro_utils.common.compose

import org.jetbrains.compose.web.css.*

object SkeletonAnimation : StyleSheet() {
    val skeletonKeyFrames: CSSNamedKeyframes by keyframes {
        to { backgroundPosition("-20% 0") }
    }

    fun CSSBuilder.createSkeletonStyle(
        duration: CSSSizeValue<out CSSUnitTime> = 2.s,
        timingFunction: AnimationTimingFunction = AnimationTimingFunction.EaseInOut,
        iterationCount: Int? = null,
        direction: AnimationDirection = AnimationDirection.Normal,
        keyFrames: CSSNamedKeyframes = skeletonKeyFrames
    ) {
        backgroundImage("linear-gradient(110deg, rgb(236, 236, 236) 40%, rgb(245, 245, 245) 50%, rgb(236, 236, 236) 65%)")
        backgroundSize("200% 100%")
        backgroundPosition("180% 0")
        animation(keyFrames) {
            duration(duration)
            timingFunction(timingFunction)
            iterationCount(iterationCount)
            direction(direction)
        }
        property("color", "${Color.transparent} !important")

        child(self, universal) style {
            property("visibility", "hidden")
        }
    }

    val skeleton by style {
        createSkeletonStyle()
    }
}

