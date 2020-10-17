package dev.inmo.micro_utils.repos.exposed

import dev.inmo.micro_utils.repos.Repo
import org.jetbrains.exposed.sql.Database

interface ExposedRepo : Repo {
    val database: Database

    fun onInit()
}