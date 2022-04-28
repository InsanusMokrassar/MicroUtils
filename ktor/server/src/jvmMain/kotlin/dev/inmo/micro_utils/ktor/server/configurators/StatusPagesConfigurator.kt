package dev.inmo.micro_utils.ktor.server.configurators

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.plugins.statuspages.StatusPagesConfig
import kotlinx.serialization.Contextual

class StatusPagesConfigurator(
    private val elements: List<@Contextual Element>
) : KtorApplicationConfigurator {
    fun interface Element { operator fun StatusPagesConfig.invoke() }

    override fun Application.configure() {
        install(StatusPages) {
            elements.forEach {
                it.apply { invoke() }
            }
        }
    }
}

