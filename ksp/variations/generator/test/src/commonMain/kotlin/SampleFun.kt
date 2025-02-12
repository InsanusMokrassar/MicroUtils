import dev.inmo.micro_utils.ksp.variations.GenerateVariations
import dev.inmo.micro_utils.ksp.variations.GenerationVariant

data class Sample(
    val value: String
)

@GenerateVariations
fun sample(
    @GenerationVariant(
        "example",
        "Sample",
        "value"
    )
    example: String = "12"
) = println(example)

@GenerateVariations
suspend fun Sample.sample2(
    @GenerationVariant(
        "arg12",
        "kotlin.Int",
        "toString()"
    )
    arg1: String = "1",
    @GenerationVariant(
        "arg22",
        "kotlin.String",
        "toInt()"
    )
    arg2: Int = 2,
    arg3: Boolean = false
) = println(arg1)
