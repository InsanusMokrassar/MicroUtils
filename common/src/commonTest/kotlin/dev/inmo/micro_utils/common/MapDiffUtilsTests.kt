package dev.inmo.micro_utils.common

import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MapDiffUtilsTests {

    private fun <K, V> compareFun(wasCalled: BooleanArray): (K, V, V) -> Boolean = { _, a, b ->
        wasCalled[0] = true
        a != b
    }
    private val compareFunWasCalled = booleanArrayOf(false)

    private val originalMap = mapOf(
        "a" to 1,
        "b" to 2,
        "c" to 3,
    )
    private val newMapRemoved = Pair(
        mapOf(
            "a" to 1,
            "c" to 3,
        ),
        mapOf("b" to 2)
    )
    private val newMapAdded = Pair(
        mapOf(
            "a" to 1,
            "b" to 2,
            "c" to 3,
            "d" to 4,
        ),
        mapOf("d" to 4)
    )
    private val newMapChanged = Pair(
        mapOf(
            "a" to 1,
            "b" to 2,
            "c" to 5,
        ),
        mapOf("c" to (3 to 5))
    )
    private val newMapChangedComparable = Pair(
        newMapChanged.first,
        mapOf(
            "a" to (1 to 1),
            "b" to (2 to 2),
        )
    )
    private val newMapMixed = Pair(
        mapOf(
            "a" to 1,
            "c" to 5,
            "d" to 4,
        ),
        MapDiff(
            newMapRemoved.second,
            newMapChanged.second,
            newMapAdded.second,
        )
    )

    @Test
    fun testMapDiffRemoved() {
        val diff = originalMap.diff(newMapRemoved.first)

        assertEquals(
            newMapRemoved.second,
            diff.removed
        )
    }

    @Test
    fun testMapDiffAdded() {
        val diff = originalMap.diff(newMapAdded.first)

        assertEquals(
            newMapAdded.second,
            diff.added
        )
    }

    @Test
    fun testMapDiffChanged() {
        val diff = originalMap.diff(newMapChanged.first)

        assertEquals(
            newMapChanged.second,
            diff.changed
        )
    }

    @Test
    fun testMapDiffRemovedWithCompareFun() {
        val wasCalled = compareFunWasCalled
        val diff = originalMap.diff(newMapRemoved.first, compareFun(wasCalled))

        assertTrue(wasCalled[0])
        assertEquals(
            newMapRemoved.second,
            diff.removed
        )
    }

    @Test
    fun testMapDiffAddedWithCompareFun() {
        val wasCalled = compareFunWasCalled
        val diff = originalMap.diff(newMapAdded.first, compareFun(wasCalled))

        assertTrue(wasCalled[0])
        assertEquals(
            newMapAdded.second,
            diff.added
        )
    }

    @Test
    fun testMapDiffChangedWithCompareFun() {
        val wasCalled = compareFunWasCalled
        val diff = originalMap.diff(newMapChangedComparable.first, compareFun(wasCalled))

        assertTrue(wasCalled[0])
        assertEquals(
            newMapChangedComparable.second,
            diff.changed
        )
    }

    @Test
    fun testMapDiffRemovedStrictComparison() {
        val diff = originalMap.diff(newMapRemoved.first, true)

        assertEquals(
            newMapRemoved.second,
            diff.removed
        )
    }

    @Test
    fun testMapDiffAddedStrictComparison() {
        val diff = originalMap.diff(newMapAdded.first, true)

        assertEquals(
            newMapAdded.second,
            diff.added
        )
    }

    @Test
    fun testMapDiffChangedStrictComparison() {
        val diff = originalMap.diff(newMapChanged.first, true)

        assertEquals(
            newMapChanged.second,
            diff.changed
        )
    }

    @Test
    fun testApplyMapDiffRemoved() {
        val originalMap = originalMap.toMutableMap()
        val mapDiff = MapDiff.empty<String, Int>().copy(
            removed = newMapRemoved.first
        )

        originalMap.applyDiff(mapDiff)

        assertEquals(
            newMapRemoved.second,
            originalMap
        )
    }
    @Test
    fun testApplyMapDiffAdded() {
        val originalMap = originalMap.toMutableMap()
        val mapDiff = MapDiff.empty<String, Int>().copy(
            added = newMapAdded.first
        )

        originalMap.applyDiff(mapDiff)

        assertEquals(
            newMapAdded.first,
            originalMap
        )
    }

    @Test
    fun testApplyMapDiffChanged() {
        val originalMap = originalMap.toMutableMap()
        val mapDiff = MapDiff.empty<String, Int>().copy(
            changed = newMapChanged.second
        )

        originalMap.applyDiff(mapDiff)

        assertEquals(
            newMapChanged.first,
            originalMap
        )
    }

    @Test
    fun testApplyMapDiffRemovedStrictComparison() {
        val originalMap = originalMap.toMutableMap()
        val diff = originalMap.applyDiff(newMapRemoved.first, true)

        assertEquals(
            newMapRemoved.second,
            diff.removed
        )
    }

    @Test
    fun testApplyMapDiffAddedStrictComparison() {
        val originalMap = originalMap.toMutableMap()
        val diff = originalMap.applyDiff(newMapAdded.first, true)

        assertEquals(
            newMapAdded.second,
            diff.added
        )
    }

    @Test
    fun testApplyMapDiffChangedStrictComparison() {
        val originalMap = originalMap.toMutableMap()
        val diff = originalMap.applyDiff(newMapChanged.first, true)

        assertEquals(
            newMapChanged.second,
            diff.changed
        )
    }

    @Test
    fun testApplyMapDiffRemovedWithCompareFun() {
        val wasCalled = compareFunWasCalled
        val oldMap = originalMap.toMutableMap()
        val diff = oldMap.applyDiff(newMapRemoved.first, compareFun(wasCalled))

        assertTrue(wasCalled[0])
        assertEquals(
            newMapRemoved.second,
            diff.removed
        )
    }
    @Test
    fun testApplyMapDiffAddedWithCompareFun() {
        val wasCalled = compareFunWasCalled
        val oldMap = originalMap.toMutableMap()
        val diff = oldMap.applyDiff(newMapAdded.first, compareFun(wasCalled))

        assertTrue(wasCalled[0])
        assertEquals(
            newMapAdded.second,
            diff.added
        )
    }

    @Test
    fun testApplyMapDiffChangedWithCompareFun() {
        val wasCalled = compareFunWasCalled
        val oldMap = originalMap.toMutableMap()
        val diff = oldMap.applyDiff(newMapChangedComparable.first, compareFun(wasCalled))

        assertTrue(wasCalled[0])
        assertEquals(
            newMapChangedComparable.second,
            diff.changed
        )
    }

    @Test
    fun testMapDiffMixed() {
        val oldMap = originalMap.toMutableMap()
        val diff = oldMap.applyDiff(newMapMixed.first)

        assertEquals(
            newMapMixed.second.removed,
            diff.removed
        )
        assertEquals(
            newMapMixed.second.changed,
            diff.changed
        )
        assertEquals(
            newMapMixed.second.added,
            diff.added
        )
    }

    @Test
    fun testApplyMapDiffMixed() {
        val oldMap = originalMap.toMutableMap()
        val diff = oldMap.applyDiff(newMapMixed.first)

        assertEquals(
            newMapMixed.second.removed,
            diff.removed
        )
        assertEquals(
            newMapMixed.second.changed,
            diff.changed
        )
        assertEquals(
            newMapMixed.second.added,
            diff.added
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

    @Test
    fun testApplyMapDiffWorksWithEqualValuesStrictComparison() {
        val oldMap = mutableMapOf<String, Int>()
        val newMap = mutableMapOf<String, Int>()

        for (i in 0 until 10) {
            val value = Random.nextInt()
            oldMap[i.toString()] = value
            newMap[i.toString()] = value
        }

        val mapDiff = oldMap.applyDiff(newMap, true)

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
