package dev.inmo.micro_utils.ktor.server.configurators

import dev.inmo.micro_utils.ktor.server.configurators.ApplicationRoutingConfigurator.Element
import io.ktor.server.application.*
import io.ktor.server.routing.Route
import io.ktor.server.routing.Routing
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
class ApplicationRoutingConfigurator(
    private val elements: List<@Contextual Element>
) : KtorApplicationConfigurator {
    fun interface Element { operator fun Route.invoke() }
    private val rootInstaller = Element {
        elements.forEach {
            it.apply { invoke() }
        }
    }

    override fun Application.configure() {
        pluginOrNull(Routing) ?.apply {
            rootInstaller.apply { invoke() }
        } ?: install(Routing) {
            rootInstaller.apply { invoke() }
        }
    }
}

