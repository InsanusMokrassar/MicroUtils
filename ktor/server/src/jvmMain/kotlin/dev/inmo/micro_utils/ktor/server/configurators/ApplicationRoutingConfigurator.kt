package dev.inmo.micro_utils.ktor.server.configurators

import dev.inmo.micro_utils.ktor.server.configurators.ApplicationRoutingConfigurator.Element
import io.ktor.server.application.*
import io.ktor.server.routing.*
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
class ApplicationRoutingConfigurator(
    private val elements: List<@Contextual Element>
) : KtorApplicationConfigurator {
    fun interface Element { operator fun Routing.invoke() }
    private val rootInstaller = Element {
        elements.forEach {
            it.apply { invoke() }
        }
    }

    override fun Application.configure() {
        routing {
            rootInstaller.apply { invoke() }
        }
    }
}

