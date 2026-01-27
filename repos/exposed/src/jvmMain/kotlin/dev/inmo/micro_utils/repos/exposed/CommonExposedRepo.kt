package dev.inmo.micro_utils.repos.exposed

import org.jetbrains.exposed.v1.core.Op
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.or

interface CommonExposedRepo<IdType, ObjectType> : ExposedRepo {
    val ResultRow.asObject: ObjectType
    val ResultRow.asId: IdType
    val selectById: (IdType) -> Op<Boolean>
    val selectByIds: (List<IdType>) -> Op<Boolean>
        get() = {
            if (it.isEmpty()) {
                Op.FALSE
            } else {
                var op = it.firstOrNull() ?.let { selectById(it) } ?: Op.FALSE
                var i = 1
                while (i < it.size) {
                    op = op.or(selectById(it[i]))
                    i++
                }
                op
            }
        }
}
