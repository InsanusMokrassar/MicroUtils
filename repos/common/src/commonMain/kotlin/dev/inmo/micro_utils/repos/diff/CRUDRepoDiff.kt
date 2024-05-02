package dev.inmo.micro_utils.repos.diff

import dev.inmo.micro_utils.common.MapDiff
import dev.inmo.micro_utils.common.applyDiff
import dev.inmo.micro_utils.common.diff
import dev.inmo.micro_utils.repos.CRUDRepo
import dev.inmo.micro_utils.repos.ReadCRUDRepo
import dev.inmo.micro_utils.repos.unset

suspend fun <Id, Registered> ReadCRUDRepo<Registered, Id>.diff(other: Map<Id, Registered>): MapDiff<Id, Registered> {
    return getAll().diff(other)
}

suspend fun <Id, Registered> Map<Id, Registered>.diff(other: ReadCRUDRepo<Registered, Id>): MapDiff<Id, Registered> {
    return diff(other.getAll())
}

suspend fun <Id, Registered> MutableMap<Id, Registered>.applyDiff(other: ReadCRUDRepo<Registered, Id>) {
    applyDiff(diff(other))
}