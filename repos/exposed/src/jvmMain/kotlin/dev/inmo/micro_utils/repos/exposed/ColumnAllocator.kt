package dev.inmo.micro_utils.repos.exposed

import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.Table

typealias ColumnAllocator<T> = Table.() -> Column<T>