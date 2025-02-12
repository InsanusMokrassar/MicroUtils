package dev.inmo.micro_utils.ksp.variations.generator.test

import dev.inmo.micro_utils.ksp.variations.GenerateVariations
import dev.inmo.micro_utils.ksp.variations.GenerationVariant

data class SimpleType(
    val value: String
)

data class GenericType<T>(
    val value: T
)

@GenerateVariations
fun sample(
    @GenerationVariant(
        SimpleType::class,
        "value",
    )
    @GenerationVariant(
        GenericType::class,
        "value.toString()",
        genericTypes = arrayOf(Int::class)
    )
    example: String = "12"
) = println(example)

@GenerateVariations
fun sampleVararg(
    @GenerationVariant(
        SimpleType::class,
        "value",
    )
    vararg example: String = arrayOf("12")
) = println(example.joinToString())

@GenerateVariations
suspend fun SimpleType.sample2(
    @GenerationVariant(
        Int::class,
        "toString()",
        "arg12",
    )
    arg1: String = "1",
    @GenerationVariant(
        String::class,
        "toInt()",
        "arg22",
    )
    arg2: Int = 2,
    arg3: Boolean = false
) = println(arg1)
