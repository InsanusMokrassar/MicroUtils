package dev.inmo.micro_utils.repos.diff

import dev.inmo.micro_utils.common.MapDiff
import dev.inmo.micro_utils.common.applyDiff
import dev.inmo.micro_utils.common.diff
import dev.inmo.micro_utils.repos.CRUDRepo
import dev.inmo.micro_utils.repos.ReadCRUDRepo
import dev.inmo.micro_utils.repos.unset

/**
 * Computes the difference between this [ReadCRUDRepo] and a map of items.
 * Retrieves all items from the repository and compares them with the provided map.
 *
 * @param Id The type of IDs
 * @param Registered The type of objects stored in the repository
 * @param other The map to compare against
 * @return A [MapDiff] describing added, removed, and updated items
 */
suspend fun <Id, Registered> ReadCRUDRepo<Registered, Id>.diff(other: Map<Id, Registered>): MapDiff<Id, Registered> {
    return getAll().diff(other)
}

/**
 * Computes the difference between this map and a [ReadCRUDRepo].
 * Retrieves all items from the repository and compares them with this map.
 *
 * @param Id The type of IDs
 * @param Registered The type of objects
 * @param other The repository to compare against
 * @return A [MapDiff] describing added, removed, and updated items
 */
suspend fun <Id, Registered> Map<Id, Registered>.diff(other: ReadCRUDRepo<Registered, Id>): MapDiff<Id, Registered> {
    return diff(other.getAll())
}

/**
 * Applies the difference between this map and a [ReadCRUDRepo] to this map.
 * Modifies this mutable map to match the state of the repository.
 *
 * @param Id The type of IDs
 * @param Registered The type of objects
 * @param other The repository to synchronize with
 */
suspend fun <Id, Registered> MutableMap<Id, Registered>.applyDiff(other: ReadCRUDRepo<Registered, Id>) {
    applyDiff(diff(other))
}