// THIS CODE HAVE BEEN GENERATED AUTOMATICALLY
// TO REGENERATE IT JUST DELETE FILE
// ORIGINAL FILE: SampleFun.kt
package dev.inmo.micro_utils.ksp.variations.generator.test

import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.Unit

public suspend fun SimpleType.sample2(arg12: Int): Unit = sample2(
    arg1 = with(arg12) {toString()}
)

public suspend fun SimpleType.sample2(arg12: Int, arg2: Int): Unit = sample2(
    arg1 = with(arg12) {toString()}, arg2 = arg2
)

public suspend fun SimpleType.sample2(arg12: Int, arg3: Boolean): Unit = sample2(
    arg1 = with(arg12) {toString()}, arg3 = arg3
)

public suspend fun SimpleType.sample2(
  arg12: Int,
  arg2: Int,
  arg3: Boolean,
): Unit = sample2(
    arg1 = with(arg12) {toString()}, arg2 = arg2, arg3 = arg3
)

public suspend fun SimpleType.sample2(arg22: String): Unit = sample2(
    arg2 = with(arg22) {toInt()}
)

public suspend fun SimpleType.sample2(arg1: String, arg22: String): Unit = sample2(
    arg1 = arg1, arg2 = with(arg22) {toInt()}
)

public suspend fun SimpleType.sample2(arg22: String, arg3: Boolean): Unit = sample2(
    arg2 = with(arg22) {toInt()}, arg3 = arg3
)

public suspend fun SimpleType.sample2(
  arg1: String,
  arg22: String,
  arg3: Boolean,
): Unit = sample2(
    arg1 = arg1, arg2 = with(arg22) {toInt()}, arg3 = arg3
)
