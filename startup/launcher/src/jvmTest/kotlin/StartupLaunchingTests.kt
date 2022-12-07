import dev.inmo.micro_utils.coroutines.launchSynchronously
import dev.inmo.micro_utils.startup.launcher.Config
import dev.inmo.micro_utils.startup.launcher.HelloWorldPlugin
import dev.inmo.micro_utils.startup.launcher.defaultJson
import dev.inmo.micro_utils.startup.launcher.start
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.jsonObject
import org.koin.core.context.GlobalContext
import kotlin.test.BeforeTest
import kotlin.test.Test

class StartupLaunchingTests {
    @BeforeTest
    fun resetGlobalKoinContext() {
        kotlin.runCatching { GlobalContext.stopKoin() }
    }
    @Test(timeout = 1000L)
    fun CheckThatEmptyPluginsListLeadsToEndOfMain() {
        val emptyJson = defaultJson.encodeToJsonElement(
            Config.serializer(),
            Config(emptyList())
        ).jsonObject

        runTest {
            val  job = launch {
                start(emptyJson)
            }
            job.join()
        }
    }
    @Test(timeout = 1000L)
    fun CheckThatHelloWorldPluginsListLeadsToEndOfMain() {
        val emptyJson = defaultJson.encodeToJsonElement(
            Config.serializer(),
            Config(listOf(HelloWorldPlugin))
        ).jsonObject

        runTest {
            val  job = launch {
                start(emptyJson)
            }
            job.join()
        }
    }
}
