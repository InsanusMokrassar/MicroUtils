package dev.inmo.micro_utils.ktor.server.configurators

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.cachingheaders.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.io.File

interface KtorApplicationConfigurator {
    @Serializable
    class Routing(
        private val elements: List<@Contextual Element>
    ) : KtorApplicationConfigurator {
        fun interface Element { operator fun Route.invoke() }
        private val rootInstaller = Element {
            elements.forEach {
                it.apply { invoke() }
            }
        }

        override fun Application.configure() {
            pluginOrNull(io.ktor.server.routing.Routing) ?.apply {
                rootInstaller.apply { invoke() }
            } ?: install(io.ktor.server.routing.Routing) {
                rootInstaller.apply { invoke() }
            }
        }

        /**
         * @param pathToFolder Contains [Pair]s where firsts are paths in urls and seconds are folders file paths
         * @param pathToResource Contains [Pair]s where firsts are paths in urls and seconds are packages in resources
         */
        class Static(
            private val pathToFolder: List<Pair<String, String>> = emptyList(),
            private val pathToResource: List<Pair<String, String>> = emptyList(),
        ) : Element {
            override fun Route.invoke() {
                pathToFolder.forEach {
                    staticFiles(
                        it.first,
                        File(it.second)
                    )
                }
                pathToResource.forEach {
                    staticResources(
                        it.first,
                        it.second
                    )
                }
            }
        }
    }

    class StatusPages(
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

    class Sessions(
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

    class CachingHeaders(
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

    fun Application.configure()
}
