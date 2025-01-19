package dev.inmo.micro_utils.repos.exposed

import org.jetbrains.exposed.sql.*

interface CommonExposedRepo<IdType, ObjectType> : ExposedRepo {
    val ResultRow.asObject: ObjectType
    val ResultRow.asId: IdType
    val selectById: ISqlExpressionBuilder.(IdType) -> Op<Boolean>
    val selectByIds: ISqlExpressionBuilder.(List<IdType>) -> Op<Boolean>
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
