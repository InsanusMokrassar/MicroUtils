// THIS CODE HAVE BEEN GENERATED AUTOMATICALLY
// TO REGENERATE IT JUST DELETE FILE
// ORIGINAL FILE: Test.kt
@file:Suppress(
  "unused",
  "RemoveRedundantQualifierName",
  "RedundantVisibilityModifier",
  "NOTHING_TO_INLINE",
  "UNCHECKED_CAST",
  "OPT_IN_USAGE",
)

package dev.inmo.micro_utils.ksp.classcasts.generator.test

import dev.inmo.micro_utils.ksp.classcasts.generator.test.Test
import kotlin.Suppress

public inline fun Test.aOrNull(): Test.A? = this as?
    dev.inmo.micro_utils.ksp.classcasts.generator.test.Test.A

public inline fun Test.aOrThrow(): Test.A = this as
    dev.inmo.micro_utils.ksp.classcasts.generator.test.Test.A

public inline fun <T> Test.ifA(block: (Test.A) -> T): T? = aOrNull() ?.let(block)

public inline fun Test.cOrNull(): Test.C? = this as?
    dev.inmo.micro_utils.ksp.classcasts.generator.test.Test.C

public inline fun Test.cOrThrow(): Test.C = this as
    dev.inmo.micro_utils.ksp.classcasts.generator.test.Test.C

public inline fun <T> Test.ifC(block: (Test.C) -> T): T? = cOrNull() ?.let(block)
