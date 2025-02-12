// THIS CODE HAVE BEEN GENERATED AUTOMATICALLY
// TO REGENERATE IT JUST DELETE FILE
// ORIGINAL FILE: SampleFun.kt
package dev.inmo.micro_utils.ksp.variations.generator.test

import kotlin.Unit

public fun sampleVararg(vararg example: SimpleType): Unit = sampleVararg(
    example = with(example) {map { it.value }.toTypedArray()}
)
