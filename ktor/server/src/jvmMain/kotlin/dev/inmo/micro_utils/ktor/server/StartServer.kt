package dev.inmo.micro_utils.ktor.server

import dev.inmo.micro_utils.ktor.server.configurators.KtorApplicationConfigurator
import io.ktor.server.application.*
import io.ktor.server.cio.CIO
import io.ktor.server.cio.CIOApplicationEngine
import io.ktor.server.engine.*
import kotlin.random.Random

fun <TEngine : ApplicationEngine, TConfiguration : ApplicationEngine.Configuration> createKtorServer(
    engine: ApplicationEngineFactory<TEngine, TConfiguration>,
    host: String = "localhost",
    port: Int = Random.nextInt(1024, 65535),
    additionalEngineEnvironmentConfigurator: EngineConnectorBuilder.() -> Unit = {},
    additionalConfigurationConfigurator: TConfiguration.() -> Unit = {},
    environment: ApplicationEnvironment = applicationEnvironment(),
    block: Application.() -> Unit
): EmbeddedServer<TEngine, TConfiguration> = embeddedServer<TEngine, TConfiguration>(
    engine,
    environment,
    {
        connector {
            this.host = host
            this.port = port
            additionalEngineEnvironmentConfigurator()
        }
        additionalConfigurationConfigurator()
    },
    module = block
)

/**
 * Create server with [CIO] server engine without starting of it
 *
 * @see ApplicationEngine.start
 */
fun createKtorServer(
    host: String = "localhost",
    port: Int = Random.nextInt(1024, 65535),
    additionalEngineEnvironmentConfigurator: EngineConnectorBuilder.() -> Unit = {},
    additionalConfigurationConfigurator: CIOApplicationEngine.Configuration.() -> Unit = {},
    environment: ApplicationEnvironment = applicationEnvironment(),
    block: Application.() -> Unit
): EmbeddedServer<CIOApplicationEngine, CIOApplicationEngine.Configuration> = createKtorServer(
    CIO,
    host,
    port,
    additionalEngineEnvironmentConfigurator,
    additionalConfigurationConfigurator,
    environment,
    block
)

fun <TEngine : ApplicationEngine, TConfiguration : ApplicationEngine.Configuration> createKtorServer(
    engine: ApplicationEngineFactory<TEngine, TConfiguration>,
    host: String = "localhost",
    port: Int = Random.nextInt(1024, 65535),
    additionalEngineEnvironmentConfigurator: EngineConnectorBuilder.() -> Unit = {},
    additionalConfigurationConfigurator: TConfiguration.() -> Unit = {},
    environment: ApplicationEnvironment = applicationEnvironment(),
    configurators: List<KtorApplicationConfigurator>
): EmbeddedServer<TEngine, TConfiguration> = createKtorServer(
    engine,
    host,
    port,
    additionalEngineEnvironmentConfigurator,
    additionalConfigurationConfigurator,
    environment,
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
    additionalEngineEnvironmentConfigurator: EngineConnectorBuilder.() -> Unit = {},
    additionalConfigurationConfigurator: CIOApplicationEngine.Configuration.() -> Unit = {},
    environment: ApplicationEnvironment = applicationEnvironment(),
): EmbeddedServer<CIOApplicationEngine, CIOApplicationEngine.Configuration> = createKtorServer(CIO, host, port, additionalEngineEnvironmentConfigurator, additionalConfigurationConfigurator, environment, configurators)
