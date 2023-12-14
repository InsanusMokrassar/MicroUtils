@file:Suppress("NOTHING_TO_INLINE")

package dev.inmo.micro_utils.common

import kotlinx.serialization.Serializable

private inline fun <T> getObject(
    additional: MutableList<T>,
    iterator: Iterator<T>
): T? = when {
    additional.isNotEmpty() -> additional.removeFirst()
    iterator.hasNext() -> iterator.next()
    else -> null
}

/**
 * Diff object which contains information about differences between two [Iterable]s
 *
 * See tests for more info
 *
 * @param removed The objects which has been presented in the old collection but absent in new one. Index here is the index in the old collection
 * @param added The object which appear in new collection only. Indexes here show the index in the new collection
 * @param replaced Pair of old-new changes. First object has been presented in the old collection on its
 * [IndexedValue.index] place, the second one is the object in new collection. Both have indexes due to the fact that in
 * case when some value has been replaced after adds or removes in original collection the object index will be changed
 *
 * @see calculateDiff
 */
@Serializable
data class Diff<T> @Warning(warning) constructor(
    val removed: List<@Serializable(IndexedValueSerializer::class) IndexedValue<T>>,
    /**
     * Old-New values pairs
     */
    val replaced: List<Pair<@Serializable(IndexedValueSerializer::class) IndexedValue<T>, @Serializable(IndexedValueSerializer::class) IndexedValue<T>>>,
    val added: List<@Serializable(IndexedValueSerializer::class) IndexedValue<T>>
) {
    fun isEmpty(): Boolean = removed.isEmpty() && replaced.isEmpty() && added.isEmpty()

    companion object {
        private const val warning = "This feature can be changed without any warranties. Use with caution and only in case you know what you are doing"
    }
}

fun <T> emptyDiff(): Diff<T> = Diff(emptyList(), emptyList(), emptyList())

private inline fun <T> performChanges(
    potentialChanges: MutableList<Pair<IndexedValue<T>?, IndexedValue<T>?>>,
    additionsInOld: MutableList<T>,
    additionsInNew: MutableList<T>,
    changedList: MutableList<Pair<IndexedValue<T>, IndexedValue<T>>>,
    removedList: MutableList<IndexedValue<T>>,
    addedList: MutableList<IndexedValue<T>>,
    comparisonFun: (T?, T?) -> Boolean
) {
    var i = -1
    val (oldObject, newObject) = potentialChanges.lastOrNull() ?: return
    for ((old, new) in potentialChanges.take(potentialChanges.size - 1)) {
        i++
        val oldOneEqualToNewObject = comparisonFun(old ?.value, newObject ?.value)
        val newOneEqualToOldObject = comparisonFun(new ?.value, oldObject ?.value)
        if (oldOneEqualToNewObject || newOneEqualToOldObject) {
            changedList.addAll(
                potentialChanges.take(i).mapNotNull {
                    @Suppress("UNCHECKED_CAST")
                    if (it.first != null && it.second != null) it as Pair<IndexedValue<T>, IndexedValue<T>> else null
                }
            )
            val newPotentials = potentialChanges.drop(i).take(potentialChanges.size - i)
            when {
                oldOneEqualToNewObject -> {
                    newPotentials.first().second ?.let { addedList.add(it) }
                    newPotentials.drop(1).take(newPotentials.size - 2).forEach { (oldOne, newOne) ->
                        addedList.add(newOne!!)
                        oldOne ?.let { additionsInOld.add(oldOne.value) }
                    }
                    if (newPotentials.size > 1) {
                        newPotentials.last().first ?.value ?.let { additionsInOld.add(it) }
                    }
                }
                newOneEqualToOldObject -> {
                    newPotentials.first().first ?.let { removedList.add(it) }
                    newPotentials.drop(1).take(newPotentials.size - 2).forEach { (oldOne, newOne) ->
                        removedList.add(oldOne!!)
                        newOne ?.let { additionsInNew.add(newOne.value) }
                    }
                    if (newPotentials.size > 1) {
                        newPotentials.last().second ?.value ?.let { additionsInNew.add(it) }
                    }
                }
            }
            potentialChanges.clear()
            return
        }
    }
    if (potentialChanges.isNotEmpty() && potentialChanges.last().let { it.first == null && it.second == null }) {
        potentialChanges.dropLast(1).forEach { (old, new) ->
            when {
                old != null && new != null -> changedList.add(old to new)
                old != null -> removedList.add(old)
                new != null -> addedList.add(new)
            }
        }
    }
}

/**
 * Calculating [Diff] object
 *
 * @param strictComparison If this parameter set to true, objects which are not equal by links will be used as different
 * objects. For example, in case of two "Example" string they will be equal by value, but CAN be different by links
 */
fun <T> Iterable<T>.calculateDiff(
    other: Iterable<T>,
    comparisonFun: (T?, T?) -> Boolean
): Diff<T> {
    var i = -1
    var j = -1

    val additionalInOld = mutableListOf<T>()
    val additionalInNew = mutableListOf<T>()

    val oldIterator = iterator()
    val newIterator = other.iterator()

    val potentiallyChangedObjects = mutableListOf<Pair<IndexedValue<T>?, IndexedValue<T>?>>()
    val changedObjects = mutableListOf<Pair<IndexedValue<T>, IndexedValue<T>>>()
    val addedObjects = mutableListOf<IndexedValue<T>>()
    val removedObjects = mutableListOf<IndexedValue<T>>()

    while (true) {
        i++
        j++

        val oldObject = getObject(additionalInOld, oldIterator)
        val newObject = getObject(additionalInNew, newIterator)

        if (oldObject == null && newObject == null) {
            break
        }

        when {
            comparisonFun(oldObject, newObject) -> {
                changedObjects.addAll(potentiallyChangedObjects.map {
                    @Suppress("UNCHECKED_CAST")
                    it as Pair<IndexedValue<T>, IndexedValue<T>>
                })
                potentiallyChangedObjects.clear()
            }
            else -> {
                potentiallyChangedObjects.add(oldObject ?.let { IndexedValue(i, oldObject) } to newObject ?.let { IndexedValue(j, newObject) })
                val previousOldsAdditionsSize = additionalInOld.size
                val previousNewsAdditionsSize = additionalInNew.size
                performChanges(potentiallyChangedObjects, additionalInOld, additionalInNew, changedObjects, removedObjects, addedObjects, comparisonFun)
                i -= (additionalInOld.size - previousOldsAdditionsSize)
                j -= (additionalInNew.size - previousNewsAdditionsSize)
            }
        }
    }
    potentiallyChangedObjects.add(null to null)
    performChanges(potentiallyChangedObjects, additionalInOld, additionalInNew, changedObjects, removedObjects, addedObjects, comparisonFun)

    return Diff(removedObjects.toList(), changedObjects.toList(), addedObjects.toList())
}

/**
 * Calculating [Diff] object
 *
 * @param strictComparison If this parameter set to true, objects which are not equal by links will be used as different
 * objects. For example, in case of two "Example" string they will be equal by value, but CAN be different by links
 */
fun <T> Iterable<T>.calculateDiff(
    other: Iterable<T>,
    strictComparison: Boolean = false
): Diff<T> = calculateDiff(
    other,
    comparisonFun = if (strictComparison) {
        { t1, t2 ->
            t1 === t2
        }
    } else {
        { t1, t2 ->
            t1 === t2 || t1 == t2 // small optimization for cases when t1 and t2 are the same - comparison will be faster potentially
        }
    }
)
inline fun <T> Iterable<T>.diff(
    other: Iterable<T>,
    strictComparison: Boolean = false
): Diff<T> = calculateDiff(other, strictComparison)
inline fun <T> Iterable<T>.diff(
    other: Iterable<T>,
    noinline comparisonFun: (T?, T?) -> Boolean
): Diff<T> = calculateDiff(other, comparisonFun)

inline fun <T> Diff(old: Iterable<T>, new: Iterable<T>) = old.calculateDiff(new, strictComparison = false)
inline fun <T> StrictDiff(old: Iterable<T>, new: Iterable<T>) = old.calculateDiff(new, true)

/**
 * This method call [calculateDiff] with strict mode enabled
 */
inline fun <T> Iterable<T>.calculateStrictDiff(
    other: Iterable<T>
) = calculateDiff(other, strictComparison = true)

/**
 * Applies [diff] to [this] [MutableList]
 */
fun <T> MutableList<T>.applyDiff(
    diff: Diff<T>
) {
    for (i in diff.removed.indices.sortedDescending()) {
        removeAt(diff.removed[i].index)
    }
    diff.added.forEach { (i, t) ->
        add(i, t)
    }
    diff.replaced.forEach { (_, new) ->
        set(new.index, new.value)
    }
}

/**
 * This method call [calculateDiff] with strict mode [strictComparison] and then apply differences to [this]
 * mutable list
 */
fun <T> MutableList<T>.applyDiff(
    source: Iterable<T>,
    strictComparison: Boolean = false
): Diff<T> = calculateDiff(source, strictComparison).also {
    applyDiff(it)
}

/**
 * This method call [calculateDiff] and then apply differences to [this]
 * mutable list
 */
fun <T> MutableList<T>.applyDiff(
    source: Iterable<T>,
    comparisonFun: (T?, T?) -> Boolean
): Diff<T> = calculateDiff(source, comparisonFun).also {
    applyDiff(it)
}

/**
 * Reverse [this] [Diff]. Result will contain [Diff.added] on [Diff.removed] (and vice-verse), all the
 * [Diff.replaced] values will be reversed too
 */
fun <T> Diff<T>.reversed() = Diff(
    removed = added,
    replaced = replaced.map { it.second to it.first },
    added = removed
)
