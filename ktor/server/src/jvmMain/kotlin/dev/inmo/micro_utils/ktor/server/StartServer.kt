package dev.inmo.micro_utils.ktor.server

import dev.inmo.micro_utils.ktor.server.configurators.KtorApplicationConfigurator
import io.ktor.application.Application
import io.ktor.server.cio.CIO
import io.ktor.server.cio.CIOApplicationEngine
import io.ktor.server.engine.*
import kotlin.random.Random

fun <TEngine : ApplicationEngine, TConfiguration : ApplicationEngine.Configuration> createKtorServer(
    engine: ApplicationEngineFactory<TEngine, TConfiguration>,
    host: String = "localhost",
    port: Int = Random.nextInt(1024, 65535),
    additionalEngineEnvironmentConfigurator: ApplicationEngineEnvironmentBuilder.() -> Unit = {},
    additionalConfigurationConfigurator: TConfiguration.() -> Unit = {},
    block: Application.() -> Unit
): TEngine = embeddedServer(
    engine,
    applicationEngineEnvironment {
        module(block)
        connector {
            this.host = host
            this.port = port
        }
        additionalEngineEnvironmentConfigurator()
    },
    additionalConfigurationConfigurator
)

/**
 * Create server with [CIO] server engine without starting of it
 *
 * @see ApplicationEngine.start
 */
fun createKtorServer(
    host: String = "localhost",
    port: Int = Random.nextInt(1024, 65535),
    additionalEngineEnvironmentConfigurator: ApplicationEngineEnvironmentBuilder.() -> Unit = {},
    additionalConfigurationConfigurator: CIOApplicationEngine.Configuration.() -> Unit = {},
    block: Application.() -> Unit
): CIOApplicationEngine = createKtorServer(
    CIO,
    host,
    port,
    additionalEngineEnvironmentConfigurator,
    additionalConfigurationConfigurator,
    block
)

fun <TEngine : ApplicationEngine, TConfiguration : ApplicationEngine.Configuration> createKtorServer(
    engine: ApplicationEngineFactory<TEngine, TConfiguration>,
    host: String = "localhost",
    port: Int = Random.nextInt(1024, 65535),
    additionalEngineEnvironmentConfigurator: ApplicationEngineEnvironmentBuilder.() -> Unit = {},
    additionalConfigurationConfigurator: TConfiguration.() -> Unit = {},
    configurators: List<KtorApplicationConfigurator>
): TEngine = createKtorServer(
    engine,
    host,
    port,
    additionalEngineEnvironmentConfigurator,
    additionalConfigurationConfigurator
) {
    configurators.forEach { it.apply { configure() } }
}

/**
 * Create server with [CIO] server engine without starting of it
 *
 * @see ApplicationEngine.start
 */
fun createKtorServer(
    host: String = "localhost",
    port: Int = Random.nextInt(1024, 65535),
    configurators: List<KtorApplicationConfigurator>,
    additionalEngineEnvironmentConfigurator: ApplicationEngineEnvironmentBuilder.() -> Unit = {},
    additionalConfigurationConfigurator: CIOApplicationEngine.Configuration.() -> Unit = {},
): ApplicationEngine = createKtorServer(CIO, host, port, additionalEngineEnvironmentConfigurator, additionalConfigurationConfigurator, configurators)
