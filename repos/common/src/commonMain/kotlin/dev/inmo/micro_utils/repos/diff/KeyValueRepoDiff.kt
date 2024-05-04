package dev.inmo.micro_utils.repos.diff

import dev.inmo.micro_utils.common.MapDiff
import dev.inmo.micro_utils.common.applyDiff
import dev.inmo.micro_utils.common.diff
import dev.inmo.micro_utils.repos.KeyValueRepo
import dev.inmo.micro_utils.repos.ReadKeyValueRepo
import dev.inmo.micro_utils.repos.unset

suspend fun <Id, Registered> ReadKeyValueRepo<Id, Registered>.diff(other: Map<Id, Registered>): MapDiff<Id, Registered> {
    return getAll().diff(other)
}

suspend fun <Id, Registered> Map<Id, Registered>.diff(other: ReadKeyValueRepo<Id, Registered>): MapDiff<Id, Registered> {
    return diff(other.getAll())
}

suspend fun <Id, Registered> KeyValueRepo<Id, Registered>.applyDiff(diff: MapDiff<Id, Registered>) {
    unset(diff.removed.map { it.key })
    set(
        diff.changed.map { (k, oldNew) ->
            k to oldNew.second
        }.toMap() + diff.added
    )
}

suspend fun <Id, Registered> KeyValueRepo<Id, Registered>.applyDiff(other: Map<Id, Registered>) {
    applyDiff(diff(other))
}

suspend fun <Id, Registered> MutableMap<Id, Registered>.applyDiff(other: ReadKeyValueRepo<Id, Registered>) {
    applyDiff(diff(other))
}