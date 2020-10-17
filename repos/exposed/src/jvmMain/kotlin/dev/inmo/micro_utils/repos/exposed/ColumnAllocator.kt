package dev.inmo.micro_utils.repos.exposed

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

typealias ColumnAllocator<T> = Table.() -> Column<T>