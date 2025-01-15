import dev.inmo.micro_utils.ksp.sealed.generator.test.subtypes
import dev.inmo.micro_utils.ksp.sealed.generator.test.values
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestTests {
    @Test
    fun testThatAfterCompilationTestWorkaroundsHaveCorrectValues() {
        val correctValues = arrayOf(
            dev.inmo.micro_utils.ksp.sealed.generator.test.Test.C,
            dev.inmo.micro_utils.ksp.sealed.generator.test.Test.A,
        )
        val correctSubtypes = arrayOf(
            dev.inmo.micro_utils.ksp.sealed.generator.test.Test.C::class,
            dev.inmo.micro_utils.ksp.sealed.generator.test.Test.B::class,
        )

        assertEquals(
            correctValues.size, dev.inmo.micro_utils.ksp.sealed.generator.test.Test.values().size
        )
        correctValues.forEachIndexed { index, value ->
            assertTrue(
                value === dev.inmo.micro_utils.ksp.sealed.generator.test.Test.values().elementAt(index)
            )
        }

        assertEquals(
            correctSubtypes.size, dev.inmo.micro_utils.ksp.sealed.generator.test.Test.subtypes().size
        )
        correctSubtypes.forEachIndexed { index, value ->
            assertTrue(
                value.qualifiedName != null
            )
            assertTrue(
                value.qualifiedName === dev.inmo.micro_utils.ksp.sealed.generator.test.Test.subtypes().elementAt(index).qualifiedName
            )
        }
    }
}