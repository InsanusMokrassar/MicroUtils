package dev.inmo.micro_utils.repos.exposed

import org.jetbrains.exposed.sql.*

interface ExposedCRUDRepo<ObjectType, IdType> {
    val database: Database

    val ResultRow.asObject: ObjectType
    val selectById: SqlExpressionBuilder.(IdType) -> Op<Boolean>
}
