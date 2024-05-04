package dev.inmo.micro_utils.repos.diff

import dev.inmo.micro_utils.common.MapDiff
import dev.inmo.micro_utils.common.applyDiff
import dev.inmo.micro_utils.common.diff
import dev.inmo.micro_utils.repos.*

suspend fun <Id, Registered> ReadKeyValuesRepo<Id, Registered>.diff(other: Map<Id, List<Registered>>): MapDiff<Id, List<Registered>> {
    return getAll().diff(other)
}

suspend fun <Id, Registered> Map<Id, List<Registered>>.diff(other: ReadKeyValuesRepo<Id, Registered>): MapDiff<Id, List<Registered>> {
    return diff(other.getAll())
}

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

suspend fun <Id, Registered> KeyValuesRepo<Id, Registered>.applyDiff(other: Map<Id, List<Registered>>) {
    applyDiff(diff(other))
}

suspend fun <Id, Registered> MutableMap<Id, List<Registered>>.applyDiff(other: ReadKeyValuesRepo<Id, Registered>) {
    applyDiff(diff(other))
}