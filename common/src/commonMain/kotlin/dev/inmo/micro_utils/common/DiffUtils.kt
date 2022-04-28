@file:Suppress("NOTHING_TO_INLINE")

package dev.inmo.micro_utils.common

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
 * @see calculateDiff
 */
data class Diff<T> internal constructor(
    val removed: List<IndexedValue<T>>,
    /**
     * Old-New values pairs
     */
    val replaced: List<Pair<IndexedValue<T>, IndexedValue<T>>>,
    val added: List<IndexedValue<T>>
)

private inline fun <T> performChanges(
    potentialChanges: MutableList<Pair<IndexedValue<T>?, IndexedValue<T>?>>,
    additionsInOld: MutableList<T>,
    additionsInNew: MutableList<T>,
    changedList: MutableList<Pair<IndexedValue<T>, IndexedValue<T>>>,
    removedList: MutableList<IndexedValue<T>>,
    addedList: MutableList<IndexedValue<T>>,
    strictComparison: Boolean
) {
    var i = -1
    val (oldObject, newObject) = potentialChanges.lastOrNull() ?: return
    for ((old, new) in potentialChanges.take(potentialChanges.size - 1)) {
        i++
        val oldOneEqualToNewObject = old ?.value === newObject ?.value || (old ?.value == newObject ?.value && !strictComparison)
        val newOneEqualToOldObject = new ?.value === oldObject ?.value || (new ?.value == oldObject ?.value && !strictComparison)
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
    strictComparison: Boolean = false
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
            oldObject === newObject || (oldObject == newObject && !strictComparison) -> {
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
                performChanges(potentiallyChangedObjects, additionalInOld, additionalInNew, changedObjects, removedObjects, addedObjects, strictComparison)
                i -= (additionalInOld.size - previousOldsAdditionsSize)
                j -= (additionalInNew.size - previousNewsAdditionsSize)
            }
        }
    }
    potentiallyChangedObjects.add(null to null)
    performChanges(potentiallyChangedObjects, additionalInOld, additionalInNew, changedObjects, removedObjects, addedObjects, strictComparison)

    return Diff(removedObjects.toList(), changedObjects.toList(), addedObjects.toList())
}
inline fun <T> Iterable<T>.diff(
    other: Iterable<T>,
    strictComparison: Boolean = false
): Diff<T> = calculateDiff(other, strictComparison)

inline fun <T> Diff(old: Iterable<T>, new: Iterable<T>) = old.calculateDiff(new)
inline fun <T> StrictDiff(old: Iterable<T>, new: Iterable<T>) = old.calculateDiff(new, true)

/**
 * This method call [calculateDiff] with strict mode enabled
 */
inline fun <T> Iterable<T>.calculateStrictDiff(
    other: Iterable<T>
) = calculateDiff(other, strictComparison = true)

/**
 * This method call [calculateDiff] with strict mode [strictComparison] and then apply differences to [this]
 * mutable list
 */
fun <T> MutableList<T>.applyDiff(
    source: Iterable<T>,
    strictComparison: Boolean = false
) = calculateDiff(source, strictComparison).let {
    for (i in it.removed.indices.sortedDescending()) {
        removeAt(it.removed[i].index)
    }
    it.added.forEach { (i, t) ->
        add(i, t)
    }
    it.replaced.forEach { (_, new) ->
        set(new.index, new.value)
    }
}
