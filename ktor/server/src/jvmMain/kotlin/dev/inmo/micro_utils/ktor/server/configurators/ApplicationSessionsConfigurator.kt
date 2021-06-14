package dev.inmo.micro_utils.ktor.server.configurators

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.sessions.Sessions
import kotlinx.serialization.Contextual

class ApplicationSessionsConfigurator(
    private val elements: List<@Contextual Element>
) : KtorApplicationConfigurator {
    fun interface Element { operator fun Sessions.Configuration.invoke() }

    override fun Application.configure() {
        install(Sessions) {
            elements.forEach {
                it.apply { invoke() }
            }
        }
    }
}
