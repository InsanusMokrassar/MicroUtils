package dev.inmo.micro_utils.common

/**
 * Contains diff based on the comparison of objects with the same [K].
 *
 * @param removed Contains map with keys removed from parent map
 * @param changed Contains map with keys values changed new map in comparison with old one
 * @param added Contains map with new keys and values
 */
data class MapDiff<K, V> @Warning(warning) constructor(
    val removed: Map<K, V>,
    val changed: Map<K, Pair<V, V>>,
    val added: Map<K, V>
) {
    fun isEmpty() = removed.isEmpty() && changed.isEmpty() && added.isEmpty()
    inline fun isNotEmpty() = !isEmpty()

    companion object {
        private const val warning = "This feature can be changed without any warranties. Use with caution and only in case you know what you are doing"
        fun <K, V> empty() = MapDiff<K, V>(emptyMap(), emptyMap(), emptyMap())
    }
}

private inline fun <K, V> createCompareFun(
    strictComparison: Boolean
): (K, V, V) -> Boolean = if (strictComparison) {
    { _, first, second -> first === second }
} else {
    { _, first, second -> first == second }
}

/**
 * Compare [this] [Map] with the [other] one in principle when [other] is newer than [this]
 *
 * @param compareFun Will be used to determine changed values
 */
fun <K, V> Map<K, V>.diff(
    other: Map<K, V>,
    compareFun: (K, V, V) -> Boolean
): MapDiff<K, V> {
    val removed: Map<K, V> = (keys - other.keys).associateWith {
        getValue(it)
    }
    val added: Map<K, V> = (other.keys - keys).associateWith {
        other.getValue(it)
    }
    val changed = keys.intersect(other.keys).mapNotNull {
        val old = getValue(it)
        val new = other.getValue(it)
        if (compareFun(it, old, new)) {
            return@mapNotNull null
        } else {
            it to (old to new)
        }
    }.toMap()

    return MapDiff(
        removed,
        changed,
        added
    )
}

/**
 * Compare [this] [Map] with the [other] one in principle when [other] is newer than [this]
 *
 * @param strictComparison If true, will use strict (===) comparison for the values' comparison. Otherwise, standard
 * `equals` will be used
 */
fun <K, V> Map<K, V>.diff(
    other: Map<K, V>,
    strictComparison: Boolean = false
): MapDiff<K, V> = diff(
    other,
    compareFun = createCompareFun(strictComparison)
)

/**
 * Will apply [mapDiff] to [this] [MutableMap]
 */
fun <K, V> MutableMap<K, V>.applyDiff(
    mapDiff: MapDiff<K, V>
) {
    mapDiff.apply {
        keys.removeAll(removed.keys)
        changed.forEach { (k, oldNew) ->
            put(k, oldNew.second)
        }
        putAll(added)
    }
}

/**
 * Will apply changes with [from] map into [this] one
 *
 * @param compareFun Will be used to determine changed values
 *
 * @return [MapDiff] applied to [this] [MutableMap]
 */
fun <K, V> MutableMap<K, V>.applyDiff(
    from: Map<K, V>,
    compareFun: (K, V, V) -> Boolean
): MapDiff<K, V> {
    return diff(from, compareFun).also {
        applyDiff(it)
    }
}

/**
 * Will apply changes with [from] map into [this] one
 *
 * @param strictComparison If true, will use strict (===) comparison for the values' comparison. Otherwise, standard
 * `equals` will be used
 *
 * @return [MapDiff] applied to [this] [MutableMap]
 */
fun <K, V> MutableMap<K, V>.applyDiff(
    from: Map<K, V>,
    strictComparison: Boolean = false
): MapDiff<K, V> = applyDiff(
    from,
    compareFun = createCompareFun(strictComparison)
)

/**
 * Reverse [this] [MapDiff]. Result will contain [MapDiff.added] on [MapDiff.removed] (and vice-verse), all the
 * [MapDiff.changed] values will be reversed too
 */
fun <K, V> MapDiff<K, V>.reversed(): MapDiff<K, V> = MapDiff(
    removed = added,
    changed = changed.mapValues { (_, oldNew) -> oldNew.second to oldNew.first },
    added = removed
)
