package dev.inmo.micro_utils.repos.exposed

import org.jetbrains.exposed.sql.*

interface CommonExposedRepo<IdType, ObjectType> : ExposedRepo {
    val ResultRow.asObject: ObjectType
    val selectById: SqlExpressionBuilder.(IdType) -> Op<Boolean>
    val selectByIds: SqlExpressionBuilder.(List<IdType>) -> Op<Boolean>
        get() = { list ->
            if (list.isEmpty()) {
                Op.FALSE
            } else {
                var op = selectById(list.first())
                (1 until list.size).forEach {
                    op = op.and(selectById(list[it]))
                }
                op
            }
        }
}
