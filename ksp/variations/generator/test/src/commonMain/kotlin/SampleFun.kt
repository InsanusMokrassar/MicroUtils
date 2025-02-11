import dev.inmo.micro_utils.ksp.variations.GenerateVariations
import dev.inmo.micro_utils.ksp.variations.GenerationVariant

data class Sample(
    val value: String
)

@GenerateVariations
fun sample(
    @GenerationVariant(
        "example",
        Sample::class,
        "value"
    )
    example: String = "12"
) = println(example)
