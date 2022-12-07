import dev.inmo.micro_utils.coroutines.launchSynchronously
import dev.inmo.micro_utils.startup.launcher.Config
import dev.inmo.micro_utils.startup.launcher.HelloWorldPlugin
import dev.inmo.micro_utils.startup.launcher.defaultJson
import dev.inmo.micro_utils.startup.launcher.start
import kotlinx.coroutines.launch
import kotlinx.serialization.json.jsonObject
import kotlin.test.Test

class StartupLaunchingTests {
    @Test(timeout = 1000L)
    fun CheckThatEmptyPluginsListLeadsToEndOfMain() {
        val emptyJson = defaultJson.encodeToJsonElement(
            Config.serializer(),
            Config(emptyList())
        ).jsonObject

        launchSynchronously {
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

        launchSynchronously {
            val  job = launch {
                start(emptyJson)
            }
            job.join()
        }
    }
}
