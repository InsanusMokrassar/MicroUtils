// THIS CODE HAVE BEEN GENERATED AUTOMATICALLY
// TO REGENERATE IT JUST DELETE FILE
// ORIGINAL FILE: SampleFun.kt
package dev.inmo.micro_utils.ksp.variations.generator.test

import kotlin.Int
import kotlin.Unit

public fun sample(example: SimpleType): Unit = sample(
    example = with(example) {value}
)

public fun sample(example: GenericType<Int>): Unit = sample(
    example = with(example) {value.toString()}
)
