package dev.inmo.micro_utils.repos.exposed

import dev.inmo.micro_utils.repos.Repo
import org.jetbrains.exposed.sql.*

interface ExposedRepo : Repo, FieldSet {
    val database: Database
    val selectAll: Transaction.() -> Query
        get() = { (this@ExposedRepo as FieldSet).selectAll() }
}
