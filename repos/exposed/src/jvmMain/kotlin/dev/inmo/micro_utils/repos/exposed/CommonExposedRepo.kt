package dev.inmo.micro_utils.repos.exposed

import org.jetbrains.exposed.sql.*

interface CommonExposedRepo<IdType, ObjectType> : ExposedRepo {
    val ResultRow.asObject: ObjectType
    val ResultRow.asId: IdType
    val selectById: ISqlExpressionBuilder.(IdType) -> Op<Boolean>
    val selectByIds: ISqlExpressionBuilder.(List<IdType>) -> Op<Boolean>
        get() = {
            it.foldRight<IdType, Op<Boolean>?>(null) { id, acc ->
                acc ?.or(selectById(id)) ?: selectById(id)
            } ?: Op.FALSE
        }
}
