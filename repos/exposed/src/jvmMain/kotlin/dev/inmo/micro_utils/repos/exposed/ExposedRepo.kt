package dev.inmo.micro_utils.repos.exposed

import dev.inmo.micro_utils.repos.Repo
import org.jetbrains.exposed.v1.core.FieldSet
import org.jetbrains.exposed.v1.core.Transaction
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.Query
import org.jetbrains.exposed.v1.jdbc.selectAll

interface ExposedRepo : Repo, FieldSet {
    val database: Database
    val selectAll: Transaction.() -> Query
        get() = { (this@ExposedRepo as FieldSet).selectAll() }
}
