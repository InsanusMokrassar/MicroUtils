package dev.inmo.micro_utils.repos.exposed

import org.jetbrains.exposed.sql.*

interface ExposedCRUDRepo<ObjectType, IdType> : ExposedRepo {
    val ResultRow.asObject: ObjectType
    val selectById: SqlExpressionBuilder.(IdType) -> Op<Boolean>
    val selectAll: Transaction.() -> Query
        get() = { (this as FieldSet).selectAll() }
}
