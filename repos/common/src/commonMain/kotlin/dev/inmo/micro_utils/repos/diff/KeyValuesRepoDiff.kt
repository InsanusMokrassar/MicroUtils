package dev.inmo.micro_utils.repos.diff

import dev.inmo.micro_utils.common.MapDiff
import dev.inmo.micro_utils.common.applyDiff
import dev.inmo.micro_utils.common.diff
import dev.inmo.micro_utils.repos.*

/**
 * Computes the difference between all entries in this [ReadKeyValuesRepo] and the given [other] map.
 *
 * @param Id The type of keys
 * @param Registered The type of values
 * @param other The map to compare against
 * @return [MapDiff] describing added, removed, and changed key-to-list entries
 */
suspend fun <Id, Registered> ReadKeyValuesRepo<Id, Registered>.diff(other: Map<Id, List<Registered>>): MapDiff<Id, List<Registered>> {
    return getAll().diff(other)
}

/**
 * Computes the difference between this map and all entries in the given [ReadKeyValuesRepo].
 *
 * @param Id The type of keys
 * @param Registered The type of values
 * @param other The repository to compare against
 * @return [MapDiff] describing added, removed, and changed key-to-list entries
 */
suspend fun <Id, Registered> Map<Id, List<Registered>>.diff(other: ReadKeyValuesRepo<Id, Registered>): MapDiff<Id, List<Registered>> {
    return diff(other.getAll())
}

/**
 * Applies the given [diff] to this [KeyValuesRepo]: clears keys in [MapDiff.removed],
 * sets entries in [MapDiff.changed] and [MapDiff.added].
 *
 * @param Id The type of keys
 * @param Registered The type of values
 * @param diff The diff to apply
 */
suspend fun <Id, Registered> KeyValuesRepo<Id, Registered>.applyDiff(diff: MapDiff<Id, List<Registered>>) {
    diff.removed.forEach {
        clear(it.key)
    }
    set(
        diff.changed.map { (k, oldNew) ->
            k to oldNew.second
        }.toMap() + diff.added
    )
}

/**
 * Computes the diff between this [KeyValuesRepo] and [other], then applies the diff to this repo.
 *
 * @param Id The type of keys
 * @param Registered The type of values
 * @param other The target map state to synchronize to
 */
suspend fun <Id, Registered> KeyValuesRepo<Id, Registered>.applyDiff(other: Map<Id, List<Registered>>) {
    applyDiff(diff(other))
}

/**
 * Computes the diff between this [MutableMap] and the given [ReadKeyValuesRepo], then applies the diff to this map.
 *
 * @param Id The type of keys
 * @param Registered The type of values
 * @param other The repository whose state to synchronize to
 */
suspend fun <Id, Registered> MutableMap<Id, List<Registered>>.applyDiff(other: ReadKeyValuesRepo<Id, Registered>) {
    applyDiff(diff(other))
}
