package dev.inmo.micro_utils.ktor.server.configurators

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CachingHeaders
import kotlinx.serialization.Contextual

data class ApplicationCachingHeadersConfigurator(
    private val elements: List<@Contextual Element>
) : KtorApplicationConfigurator {
    fun interface Element { operator fun CachingHeaders.Configuration.invoke() }

    override fun Application.configure() {
        install(CachingHeaders) {
            elements.forEach {
                it.apply { invoke() }
            }
        }
    }
}

