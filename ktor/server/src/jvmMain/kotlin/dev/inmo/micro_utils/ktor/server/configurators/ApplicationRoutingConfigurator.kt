package dev.inmo.micro_utils.ktor.server.configurators

import io.ktor.application.*
import io.ktor.routing.Route
import io.ktor.routing.Routing
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
        featureOrNull(Routing) ?.apply {
            rootInstaller.apply { invoke() }
        } ?: install(Routing) {
            rootInstaller.apply { invoke() }
        }
    }
}

