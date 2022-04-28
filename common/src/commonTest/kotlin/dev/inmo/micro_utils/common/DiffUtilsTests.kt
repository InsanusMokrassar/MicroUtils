package dev.inmo.micro_utils.common

import kotlin.math.floor
import kotlin.test.Test
import kotlin.test.assertEquals

class DiffUtilsTests {
    @Test
    fun testThatSimpleRemoveWorks() {
        val oldList = (0 until 10).toList()
        val withIndex = oldList.withIndex()

        for (count in 1 .. (floor(oldList.size.toFloat() / 2).toInt())) {
            for ((i, _) in withIndex) {
                if (i + count > oldList.lastIndex) {
                    continue
                }
                val removedSublist = oldList.subList(i, i + count)
                oldList.calculateDiff(oldList - removedSublist).apply {
                    assertEquals(
                        removedSublist.mapIndexed { j, o -> IndexedValue(i + j, o) },
                        removed
                    )
                }
            }
        }
    }

    @Test
    fun testThatSimpleAddWorks() {
        val oldList = (0 until 10).map { it.toString() }
        val withIndex = oldList.withIndex()

        for (count in 1 .. (floor(oldList.size.toFloat() / 2).toInt())) {
            for ((i, _) in withIndex) {
                if (i + count > oldList.lastIndex) {
                    continue
                }
                val addedSublist = oldList.subList(i, i + count).map { "added$it" }
                val mutable = oldList.toMutableList()
                mutable.addAll(i, addedSublist)
                oldList.calculateDiff(mutable).apply {
                    assertEquals(
                        addedSublist.mapIndexed { j, o -> IndexedValue(i + j, o) },
                        added
                    )
                }
            }
        }
    }

    @Test
    fun testThatSimpleChangesWorks() {
        val oldList = (0 until 10).map { it.toString() }
        val withIndex = oldList.withIndex()

        for (step in oldList.indices) {
            for ((i, _) in withIndex) {
                val mutable = oldList.toMutableList()
                val changes = (
                    if (step == 0) i until oldList.size else (i until oldList.size step step)
                ).map { index ->
                    IndexedValue(index, mutable[index]) to IndexedValue(index, "changed$index").also {
                        mutable[index] = it.value
                    }
                }
                oldList.calculateDiff(mutable).apply {
                    assertEquals(
                        changes,
                        replaced
                    )
                }
            }
        }
    }

    @Test
    fun testThatSimpleRemoveApplyWorks() {
        val oldList = (0 until 10).toList()
        val withIndex = oldList.withIndex()

        for (count in 1 .. (floor(oldList.size.toFloat() / 2).toInt())) {
            for ((i, _) in withIndex) {
                if (i + count > oldList.lastIndex) {
                    continue
                }
                val removedSublist = oldList.subList(i, i + count)
                val mutableOldList = oldList.toMutableList()
                val targetList = oldList - removedSublist

                mutableOldList.applyDiff(targetList)

                assertEquals(
                    targetList,
                    mutableOldList
                )
            }
        }
    }

    @Test
    fun testThatSimpleAddApplyWorks() {
        val oldList = (0 until 10).map { it.toString() }
        val withIndex = oldList.withIndex()

        for (count in 1 .. (floor(oldList.size.toFloat() / 2).toInt())) {
            for ((i, _) in withIndex) {
                if (i + count > oldList.lastIndex) {
                    continue
                }
                val addedSublist = oldList.subList(i, i + count).map { "added$it" }
                val mutable = oldList.toMutableList()
                mutable.addAll(i, addedSublist)
                val mutableOldList = oldList.toMutableList()

                mutableOldList.applyDiff(mutable)

                assertEquals(
                    mutable,
                    mutableOldList
                )
            }
        }
    }

    @Test
    fun testThatSimpleChangesApplyWorks() {
        val oldList = (0 until 10).map { it.toString() }
        val withIndex = oldList.withIndex()

        for (step in oldList.indices) {
            for ((i, _) in withIndex) {
                val mutable = oldList.toMutableList()

                val newList = if (step == 0) {
                    i until oldList.size
                } else {
                    i until oldList.size step step
                }
                newList.forEach { index ->
                    IndexedValue(index, mutable[index]) to IndexedValue(index, "changed$index").also {
                        mutable[index] = it.value
                    }
                }

                val mutableOldList = oldList.toMutableList()
                mutableOldList.applyDiff(mutable)
                assertEquals(
                    mutable,
                    mutableOldList
                )
            }
        }
    }
}
