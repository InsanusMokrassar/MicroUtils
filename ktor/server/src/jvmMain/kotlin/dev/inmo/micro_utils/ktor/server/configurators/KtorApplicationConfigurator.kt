package dev.inmo.micro_utils.ktor.server.configurators

import io.ktor.server.application.Application

interface KtorApplicationConfigurator {
    fun Application.configure()
}
