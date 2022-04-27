package dev.inmo.micro_utils.ktor.server.configurators

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.sessions.Sessions
import io.ktor.server.sessions.SessionsConfig
import kotlinx.serialization.Contextual

class ApplicationSessionsConfigurator(
    private val elements: List<@Contextual Element>
) : KtorApplicationConfigurator {
    fun interface Element { operator fun SessionsConfig.invoke() }

    override fun Application.configure() {
        install(Sessions) {
            elements.forEach {
                it.apply { invoke() }
            }
        }
    }
}
