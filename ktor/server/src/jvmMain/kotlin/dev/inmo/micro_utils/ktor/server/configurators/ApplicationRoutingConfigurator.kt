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
    interface Element { operator fun Route.invoke() }

    override fun Application.configure() {
        try {
            feature(Routing)
        } catch (e: IllegalStateException) {
            install(Routing) {
                elements.forEach {
                    it.apply { invoke() }
                }
            }
        }
    }
}

