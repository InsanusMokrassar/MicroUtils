package dev.inmo.micro_utils.repos.diff

import dev.inmo.micro_utils.common.MapDiff
import dev.inmo.micro_utils.common.applyDiff
import dev.inmo.micro_utils.common.diff
import dev.inmo.micro_utils.repos.KeyValueRepo
import dev.inmo.micro_utils.repos.ReadKeyValueRepo
import dev.inmo.micro_utils.repos.unset

/**
 * Computes the difference between all entries in this [ReadKeyValueRepo] and the given [other] map.
 *
 * @param Id The type of keys
 * @param Registered The type of values
 * @param other The map to compare against
 * @return [MapDiff] describing added, removed, and changed entries
 */
suspend fun <Id, Registered> ReadKeyValueRepo<Id, Registered>.diff(other: Map<Id, Registered>): MapDiff<Id, Registered> {
    return getAll().diff(other)
}

/**
 * Computes the difference between this map and all entries in the given [ReadKeyValueRepo].
 *
 * @param Id The type of keys
 * @param Registered The type of values
 * @param other The repository to compare against
 * @return [MapDiff] describing added, removed, and changed entries
 */
suspend fun <Id, Registered> Map<Id, Registered>.diff(other: ReadKeyValueRepo<Id, Registered>): MapDiff<Id, Registered> {
    return diff(other.getAll())
}

/**
 * Applies the given [diff] to this [KeyValueRepo]: removes entries in [MapDiff.removed],
 * updates entries in [MapDiff.changed], and adds entries in [MapDiff.added].
 *
 * @param Id The type of keys
 * @param Registered The type of values
 * @param diff The diff to apply
 */
suspend fun <Id, Registered> KeyValueRepo<Id, Registered>.applyDiff(diff: MapDiff<Id, Registered>) {
    unset(diff.removed.map { it.key })
    set(
        diff.changed.map { (k, oldNew) ->
            k to oldNew.second
        }.toMap() + diff.added
    )
}

/**
 * Computes the diff between this [KeyValueRepo] and [other], then applies the diff to this repo.
 *
 * @param Id The type of keys
 * @param Registered The type of values
 * @param other The target map state to synchronize to
 */
suspend fun <Id, Registered> KeyValueRepo<Id, Registered>.applyDiff(other: Map<Id, Registered>) {
    applyDiff(diff(other))
}

/**
 * Computes the diff between this [MutableMap] and the given [ReadKeyValueRepo], then applies the diff to this map.
 *
 * @param Id The type of keys
 * @param Registered The type of values
 * @param other The repository whose state to synchronize to
 */
suspend fun <Id, Registered> MutableMap<Id, Registered>.applyDiff(other: ReadKeyValueRepo<Id, Registered>) {
    applyDiff(diff(other))
}
