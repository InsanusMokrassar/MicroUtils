package dev.inmo.micro_utils.ksp.classcasts.generator.test

import dev.inmo.micro_utils.ksp.classcasts.ClassCastsExcluded
import dev.inmo.micro_utils.ksp.classcasts.ClassCastsIncluded

@ClassCastsIncluded
sealed interface Test {
    object A : Test
    @ClassCastsExcluded
    object B : Test
    object C : Test
}
