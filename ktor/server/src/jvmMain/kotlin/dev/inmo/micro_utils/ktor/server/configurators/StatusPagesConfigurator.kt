package dev.inmo.micro_utils.ktor.server.configurators

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.StatusPages
import kotlinx.serialization.Contextual

class StatusPagesConfigurator(
    private val elements: List<@Contextual Element>
) : KtorApplicationConfigurator {
    interface Element { operator fun StatusPages.Configuration.invoke() }

    override fun Application.configure() {
        install(StatusPages) {
            elements.forEach {
                it.apply { invoke() }
            }
        }
    }
}

