package dev.inmo.micro_utils.ksp.classcasts.generator.test

import dev.inmo.micro_utils.ksp.classcasts.ClassCastsExcluded
import dev.inmo.micro_utils.ksp.classcasts.ClassCastsIncluded

@ClassCastsIncluded(levelsToInclude = 1)
sealed interface Test {
    object A : Test
    @ClassCastsExcluded
    object B : Test // Will not be included in class casts due to annotation ClassCastsExcluded
    object C : Test
    interface D : Test {
        object DD : D // Will not be included in class casts due to levelsToInclude
    }
}
