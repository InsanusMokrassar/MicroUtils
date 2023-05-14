package dev.inmo.micro_utils.common

import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class MapDiffUtilsTests {

    private fun <K, V> compareFun(): (K, V, V) -> Boolean = { _, a, b -> a == b }

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
        val diff = originalMap.diff(newMapRemoved.first, compareFun())

        assertEquals(
            newMapRemoved.second,
            diff.removed
        )
    }

    @Test
    fun testMapDiffAddedWithCompareFun() {
        val diff = originalMap.diff(newMapAdded.first, compareFun())

        assertEquals(
            newMapAdded.second,
            diff.added
        )
    }

    @Test
    fun testMapDiffChangedWithCompareFun() {
        val diff = originalMap.diff(newMapChanged.first, compareFun())

        assertEquals(
            newMapChanged.second,
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
        val oldMap = originalMap.toMutableMap()
        val diff = oldMap.applyDiff(newMapRemoved.first, compareFun())

        assertEquals(
            newMapRemoved.second,
            diff.removed
        )
    }
    @Test
    fun testApplyMapDiffAddedWithCompareFun() {
        val oldMap = originalMap.toMutableMap()
        val diff = oldMap.applyDiff(newMapAdded.first, compareFun())

        assertEquals(
            newMapAdded.second,
            diff.added
        )
    }

    @Test
    fun testApplyMapDiffChangedWithCompareFun() {
        val oldMap = originalMap.toMutableMap()
        val diff = oldMap.applyDiff(newMapChanged.first, compareFun())

        assertEquals(
            newMapChanged.second,
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
