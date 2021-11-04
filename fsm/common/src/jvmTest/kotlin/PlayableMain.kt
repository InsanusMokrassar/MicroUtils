import dev.inmo.micro_utils.fsm.common.*
import dev.inmo.micro_utils.fsm.common.dsl.buildFSM
import dev.inmo.micro_utils.fsm.common.managers.DefaultStatesManager
import dev.inmo.micro_utils.fsm.common.managers.InMemoryStatesManager
import kotlinx.coroutines.*

sealed interface TrafficLightState : State {
    val trafficLightNumber: Int
    override val context: Int
        get() = trafficLightNumber
}
data class GreenCommon(override val trafficLightNumber: Int) : TrafficLightState
data class YellowCommon(override val trafficLightNumber: Int) : TrafficLightState
data class RedCommon(override val trafficLightNumber: Int) : TrafficLightState

class PlayableMain {
//    @Test
    fun test() {
        runBlocking {
            val countOfTrafficLights = 10
            val initialStates = (0 until countOfTrafficLights).map {
                when (0/*Random.nextInt(3)*/) {
                    0 -> GreenCommon(it)
                    1 -> YellowCommon(it)
                    else -> RedCommon(it)
                }
            }

            val statesManager = DefaultStatesManager<TrafficLightState>()

            val machine = buildFSM<TrafficLightState> {
                strictlyOn<GreenCommon> {
                    delay(1000L)
                    YellowCommon(it.context).also(::println)
                }
                strictlyOn<YellowCommon> {
                    delay(1000L)
                    RedCommon(it.context).also(::println)
                }
                strictlyOn<RedCommon> {
                    delay(1000L)
                    GreenCommon(it.context).also(::println)
                }
                this.statesManager = statesManager
            }

            initialStates.forEach { machine.startChain(it) }

            val scope = CoroutineScope(Dispatchers.Default)
            machine.start(scope).join()

        }
    }
}
