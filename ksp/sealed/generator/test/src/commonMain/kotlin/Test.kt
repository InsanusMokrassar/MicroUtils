package dev.inmo.micro_utils.ksp.sealed.generator.test

import dev.inmo.microutils.kps.sealed.GenerateSealedWorkaround

@GenerateSealedWorkaround
sealed interface Test {
    @GenerateSealedWorkaround.Order(2)
    object A : Test
    @GenerateSealedWorkaround.Exclude
    object B : Test
    @GenerateSealedWorkaround.Order(0)
    object C : Test

    // Required for successful sealed workaround generation
    companion object
}
