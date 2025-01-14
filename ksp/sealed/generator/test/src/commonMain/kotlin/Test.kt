package dev.inmo.micro_utils.ksp.sealed.generator.test

import dev.inmo.micro_utils.ksp.sealed.GenerateSealedTypesWorkaround
import dev.inmo.micro_utils.ksp.sealed.GenerateSealedWorkaround

@GenerateSealedWorkaround
@GenerateSealedTypesWorkaround
sealed interface Test {
    @GenerateSealedWorkaround.Order(2)
    @GenerateSealedTypesWorkaround.Order(2)
    object A : Test
    @GenerateSealedWorkaround.Exclude
    @GenerateSealedTypesWorkaround.Exclude
    object B : Test
    @GenerateSealedWorkaround.Order(0)
    @GenerateSealedTypesWorkaround.Order(0)
    object C : Test

    // Required for successful sealed workaround generation
    companion object
}
