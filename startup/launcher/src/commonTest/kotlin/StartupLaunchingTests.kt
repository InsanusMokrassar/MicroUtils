import dev.inmo.micro_utils.startup.launcher.Config
import dev.inmo.micro_utils.startup.launcher.HelloWorldPlugin
import dev.inmo.micro_utils.startup.launcher.StartLauncherPlugin
import dev.inmo.micro_utils.startup.launcher.defaultJson
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.jsonObject
import org.koin.core.context.stopKoin
import kotlin.test.BeforeTest
import kotlin.test.Test

class StartupLaunchingTests {
    @BeforeTest
    fun resetGlobalKoinContext() {
        runCatching { stopKoin() }
    }
    @Test
    fun CheckThatEmptyPluginsListLeadsToEndOfMain() {
        val emptyJson = defaultJson.encodeToJsonElement(
            Config.serializer(),
            Config(emptyList())
        ).jsonObject

        runTest {
            val  job = launch {
                StartLauncherPlugin.start(emptyJson)
            }
            job.join()
        }
    }
    @Test
    fun CheckThatHelloWorldPluginsListLeadsToEndOfMain() {
        val emptyJson = defaultJson.encodeToJsonElement(
            Config.serializer(),
            Config(listOf(HelloWorldPlugin))
        ).jsonObject

        runTest {
            val  job = launch {
                StartLauncherPlugin.start(emptyJson)
            }
            job.join()
        }
    }
}
