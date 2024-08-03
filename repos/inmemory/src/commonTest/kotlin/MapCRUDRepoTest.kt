import dev.inmo.micro_utils.repos.MapCRUDRepo
import dev.inmo.micro_utils.repos.create
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

data class TestModel(
    val testery: String,
    val isTestery: Boolean,
    val testeryAmount: Int
)

class MapCRUDRepoTest {
    /**
     * A utility function used for generating non-intersecting ranges in cyclic environment, like [repeat]
     * or for-loops
     */
    private fun getIdRange(start: Int, base: Int) = (start * base)..(start * base + (base - 1))

    @Test
    fun mapCrudRepoTest() = runTest {
        val base = 4 // define a bigger number to increase test intensity
        val targetMap = mutableMapOf<Int, TestModel>()

        // filling the map
        val jobs = List(base) { id ->
            launch(Dispatchers.Default) { // to make delays work
                println("launching job $id")
                delay((1..5).random().toLong())

                val idsList = getIdRange(id, base).toMutableList()
                println("created ids list $idsList")
                val mapCRUDRepo = MapCRUDRepo<TestModel, Int, TestModel>(
                    targetMap,
                    createCallback = { new ->
                        val newObject = idsList.first() to new
                        idsList.remove(newObject.first)
                        println("created $newObject in map")
                        newObject
                    },
                    updateCallback = { new, id, old ->
                        println("updated object at $id from $old to $new")
                        new
                    }
                )
                for (i in getIdRange(id, base)) {
                    val newModel = TestModel(
                        testery = "testery$i",
                        isTestery = i % 2 == 0,
                        testeryAmount = i
                    )
                    mapCRUDRepo.create(newModel)
                }
            }
        }

        jobs.forEach {
            println("joining job ${it.hashCode()}")
            it.join()
        }

        // validating the map
        val validationJobs = List(base) { id ->
            launch {
                val idsList = getIdRange(id, base).toMutableList()
                val mapCRUDRepo = MapCRUDRepo<TestModel, Int, TestModel>(
                    targetMap,
                    // these will not be used
                    createCallback = { new ->
                        val newObject = idsList.first() to new
                        idsList.remove(newObject.first)
                        println("created $newObject in map")
                        newObject
                    },
                    updateCallback = { new, id, old ->
                        println("updated object at $id from $old to $new")
                        new
                    }
                )
                for (i in getIdRange(id, base)) {
                    val newModel = TestModel(
                        testery = "testery$i",
                        isTestery = i % 2 == 0,
                        testeryAmount = i
                    )
                    val oldModel = mapCRUDRepo.getById(i)
                    assertEquals(newModel, oldModel)
                }
            }
        }

        validationJobs.forEach {
            println("joining validation job ${it.hashCode()}")
            it.join()
        }
    }

    @Test
    fun updateMapCrudRepoTest() = runTest {
        val base = 10 // define a bigger number to increase test intensity
        val targetMap = mutableMapOf<Int, TestModel>()

        // fill the map using stdlib method
        repeat(base * base) { num ->
            targetMap[num] = TestModel(
                testery = "testery$num",
                isTestery = num % 2 == 0,
                testeryAmount = num
            )
        }

        // update the map using MapCRUDRepo
        val jobs = List(base) { id ->
            launch {
                println("launching job $id")
                val idsList = getIdRange(id, base).toMutableList()
                val mapCRUDRepo = MapCRUDRepo<TestModel, Int, TestModel>(
                    targetMap,
                    updateCallback = { new, id, old ->
                        println("updated object at $id from $old to $new")
                        new
                    },
                    createCallback = { new ->
                        val newObject = idsList.first() to new
                        idsList.remove(newObject.first)
                        println("created $newObject in map")
                        newObject
                    }
                )
                for (i in getIdRange(id, base)) {
                    val newModel = TestModel(
                        testery = "testery${i}new",
                        isTestery = i % 2 == 0,
                        testeryAmount = i
                    )
                    mapCRUDRepo.update(i, newModel)
                }
            }
        }

        jobs.forEach {
            println("joining job ${it.hashCode()}")
            it.join()
        }

        // validating the map
        val validationJobs = List(base) { id ->
            launch {
                val idsList = getIdRange(id, base).toMutableList()
                val mapCRUDRepo = MapCRUDRepo<TestModel, Int, TestModel>(
                    targetMap,
                    updateCallback = { new, id, old ->
                        println("updated object at $id from $old to $new")
                        new
                    },
                    createCallback = { new ->
                        val newObject = idsList.first() to new
                        idsList.remove(newObject.first)
                        println("created $newObject in map")
                        newObject
                    }
                )
                for (i in getIdRange(id, base)) {
                    val newModel = TestModel(
                        testery = "testery${i}new",
                        isTestery = i % 2 == 0,
                        testeryAmount = i
                    )
                    val oldModel = mapCRUDRepo.getById(i)
                    assertEquals(newModel, oldModel)
                }
            }
        }

        validationJobs.forEach {
            println("joining validation job ${it.hashCode()}")
            it.join()
        }
    }
}