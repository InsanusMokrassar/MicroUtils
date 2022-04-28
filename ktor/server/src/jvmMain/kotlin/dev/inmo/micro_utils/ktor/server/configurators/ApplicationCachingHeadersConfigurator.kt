package dev.inmo.micro_utils.ktor.server.configurators

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.cachingheaders.CachingHeaders
import io.ktor.server.plugins.cachingheaders.CachingHeadersConfig
import kotlinx.serialization.Contextual

data class ApplicationCachingHeadersConfigurator(
    private val elements: List<@Contextual Element>
) : KtorApplicationConfigurator {
    fun interface Element { operator fun CachingHeadersConfig.invoke() }

    override fun Application.configure() {
        install(CachingHeaders) {
            elements.forEach {
                it.apply { invoke() }
            }
        }
    }
}

