package dev.inmo.micro_utils.serialization.typed_serializer

import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class TypedSerializerTests {
    interface Example {
        val number: Number
    }
    val serialFormat = Json {  }

    @Serializable
    data class Example1(override val number: Long) : Example

    @Serializable
    data class Example2(override val number: Double) : Example

    @Test
    fun testThatSerializerWorksCorrectly() {
        val serializer = TypedSerializer(
            mapOf(
                "long" to Example1.serializer(),
                "double" to Example2.serializer()
            )
        )

        val value1 = Example1(Random.nextLong())
        val value2 = Example2(Random.nextDouble())

        val list = listOf(value1, value2)
        val serialized = serialFormat.encodeToString(ListSerializer(serializer), list)
        val deserialized = serialFormat.decodeFromString(ListSerializer(serializer), serialized)

        assertEquals(list, deserialized)
    }
}