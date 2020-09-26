package dev.inmo.micro_utils.repos.exposed

import dev.inmo.micro_utils.repos.Repo
import org.jetbrains.exposed.sql.*

interface ExposedCRUDRepo<ObjectType, IdType> : Repo {
    val database: Database

    val ResultRow.asObject: ObjectType
    val selectById: SqlExpressionBuilder.(IdType) -> Op<Boolean>
}
