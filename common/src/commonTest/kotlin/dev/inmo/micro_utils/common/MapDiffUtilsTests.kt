package dev.inmo.micro_utils.common

import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class MapDiffUtilsTests {

    private fun <K, V> compareFun(): (K, V, V) -> Boolean = { _, a, b -> a == b }

    @Test
    fun testMapDiffRemoved() {
        val map1 = mapOf("a" to 1, "b" to 2, "c" to 3)
        val map2 = mapOf("a" to 1, "c" to 3, "d" to 4)

        val diff = map1.diff(map2)

        assertEquals(mapOf("b" to 2), diff.removed)
    }

    @Test
    fun testMapDiffAdded() {
        val map1 = mapOf("a" to 1, "b" to 2, "c" to 3)
        val map2 = mapOf("a" to 1, "c" to 3, "d" to 4)

        val diff = map1.diff(map2)

        assertEquals(mapOf("d" to 4), diff.added)
    }

    @Test
    fun testMapDiffChanged() {
        val map1 = mapOf("a" to 1, "b" to 2, "c" to 3)
        val map2 = mapOf("a" to 1, "c" to 5, "d" to 4)

        val diff = map1.diff(map2)

        assertEquals(mapOf("c" to (3 to 5)), diff.changed)
    }

    @Test
    fun testMapDiffRemovedWithCompareFun() {
        val map1 = mapOf("a" to 1, "b" to 2, "c" to 3)
        val map2 = mapOf("a" to 1, "c" to 3, "d" to 4)

        val diff = map1.diff(map2, compareFun())

        assertEquals(mapOf("b" to 2), diff.removed)
    }

    @Test
    fun testMapDiffAddedWithCompareFun() {
        val map1 = mapOf("a" to 1, "b" to 2, "c" to 3)
        val map2 = mapOf("a" to 1, "c" to 3, "d" to 4)

        val diff = map1.diff(map2, compareFun())

        assertEquals(mapOf("d" to 4), diff.added)
    }

    @Test
    fun testMapDiffChangedWithCompareFun() {
        val map1 = mapOf("a" to 1, "b" to 2, "c" to 3)
        val map2 = mapOf("a" to 1, "c" to 5, "d" to 4)

        val diff = map1.diff(map2) { _, v1, v2 -> v1 == v2 }

        assertEquals(mapOf("c" to (3 to 5)), diff.changed)
    }

    @Test
    fun testMapDiffRemovedStrictComparison() {
        val map1 = mapOf("a" to 1, "b" to 2, "c" to 3)
        val map2 = mapOf("a" to 1, "c" to 3, "d" to 4)

        val diff = map1.diff(map2, true)

        assertEquals(mapOf("b" to 2), diff.removed)
    }

    @Test
    fun testMapDiffAddedStrictComparison() {
        val map1 = mapOf("a" to 1, "b" to 2, "c" to 3)
        val map2 = mapOf("a" to 1, "c" to 3, "d" to 4)

        val diff = map1.diff(map2, true)

        assertEquals(mapOf("d" to 4), diff.added)
    }

    @Test
    fun testMapDiffChangedStrictComparison() {
        val map1 = mapOf("a" to 1, "b" to 2, "c" to 3)
        val map2 = mapOf("a" to 1, "c" to 5, "d" to 4)

        val diff = map1.diff(map2, true)

        assertEquals(mapOf("c" to (3 to 5)), diff.changed)
    }

    @Test
    @OptIn(Warning::class)
    fun testApplyMapDiffRemoved() {
        val originalMap = mutableMapOf("a" to 1, "b" to 2, "c" to 3)
        val diff = MapDiff(mapOf("b" to 2), emptyMap(), emptyMap())
        originalMap.applyDiff(diff)
        assertEquals(mapOf("a" to 1, "c" to 3), originalMap)
    }

    @Test
    @OptIn(Warning::class)
    fun testApplyMapDiffChanged() {
        val originalMap = mutableMapOf("a" to 1, "b" to 2, "c" to 3)
        val diff = MapDiff(emptyMap(), mapOf("b" to (2 to 4)), emptyMap())
        originalMap.applyDiff(diff)
        assertEquals(mapOf("a" to 1, "b" to 4, "c" to 3), originalMap)
    }

    @Test
    @OptIn(Warning::class)
    fun testApplyMapDiffAdded() {
        val originalMap = mutableMapOf("a" to 1, "b" to 2, "c" to 3)
        val diff = MapDiff(emptyMap(), emptyMap(), mapOf("d" to 4))
        originalMap.applyDiff(diff)
        assertEquals(mapOf("a" to 1, "b" to 2, "c" to 3, "d" to 4), originalMap)
    }

    @Test
    @OptIn(Warning::class)
    fun testApplyMapDiffMixed() {
        val originalMap = mutableMapOf("a" to 1, "b" to 2, "c" to 3)
        val diff = MapDiff(mapOf("b" to 2), mapOf("c" to (3 to 5)), mapOf("d" to 4))
        originalMap.applyDiff(diff)
        assertEquals(mapOf("a" to 1, "c" to 5, "d" to 4), originalMap)
    }

    @Test
    @OptIn(Warning::class)
    fun testMapDiffWorksStrictComparison() {
        val oldMap = mutableMapOf<Int, String>()
        val newMap = mutableMapOf<Int, String>()

        for (i in 0 until 10) {
            oldMap[i] = "old$i"
            newMap[i] = "new$i"
        }

        oldMap[0] = "old0"
        oldMap[2] = "old2"
        oldMap[4] = "old4"

        newMap[0] = "new0"
        newMap[2] = "new2"
        newMap[4] = "new4"
        newMap[10] = "new10"
        newMap[11] = "new11"

        newMap.remove(1)
        newMap.remove(3)

        val mapDiff = oldMap.diff(newMap, true)

        val removed = mapOf(
            1 to "old1",
            3 to "old3"
        )
        val added = mapOf(
            10 to "new10",
            11 to "new11"
        )
        val changed = oldMap.keys.intersect(newMap.keys).associateWith {
            oldMap[it] to newMap[it]
        }
        val equalMapDiff = MapDiff(removed, changed, added)

        assertEquals(
            added,
            mapDiff.added
        )
        assertEquals(
            equalMapDiff.removed,
            mapDiff.removed
        )
        assertEquals(
            equalMapDiff.changed,
            mapDiff.changed
        )
    }

    @Test
    fun testMapDiffWorksWithEqualValuesStrictComparison() {
        val oldMap = mutableMapOf<String, Int>()
        val newMap = mutableMapOf<String, Int>()

        for (i in 0 until 10) {
            val value = Random.nextInt()
            oldMap[i.toString()] = value
            newMap[i.toString()] = value
        }

        val mapDiff = oldMap.diff(newMap, true)

        val mergedValues = oldMap.keys.intersect(newMap.keys).associateWith {
            oldMap[it] to newMap[it]
        }

        assertEquals(
            mergedValues,
            mapDiff.changed,
        )
        assertEquals(
            emptyMap(),
            mapDiff.removed
        )
        assertEquals(
            emptyMap(),
            mapDiff.added
        )
    }
}
