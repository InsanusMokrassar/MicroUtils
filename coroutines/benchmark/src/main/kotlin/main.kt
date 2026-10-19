import dev.inmo.micro_utils.coroutines.SmartSemaphore
import dev.inmo.micro_utils.coroutines.withAcquire
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import java.io.File
import kotlin.math.tanh
import kotlin.system.measureNanoTime

data class Record(
    val permits: Int,
    val i: Int,
    val timeNanos: Long
)

private const val permitsCount = 1000
private val acquiresCount = 10000

suspend fun main(args: Array<String>) {
    val outputFilePath = args.firstOrNull() ?: "local.bench.txt"

    val records = ArrayList<Record>(permitsCount * acquiresCount)
    val jobs = ArrayList<Job>()
    val scope = CoroutineScope(Dispatchers.Default)
    for (permits in 1 .. permitsCount) {
        val semaphore = SmartSemaphore.Mutable(permits)
        for (i in 0 until acquiresCount) {
            jobs.add(
                scope.launch(start = CoroutineStart.LAZY) {
                    val timeNanos = measureNanoTime {
                        semaphore.withAcquire {
                            tanh(57.179)
                        }
                    }
                    records.add(Record(permits, i, timeNanos))
                }
            )
        }
    }
    jobs.joinAll()
    val file = File(outputFilePath)
    file.writeText("")
    val text = buildString {
        records.groupBy { it.permits }.forEach {
            it.value.sortedBy { it.i }.forEach {
                append("${it.permits} ${it.i} ${it.timeNanos}\n")
            }
        }
    }
    file.appendText(text)
    file.appendText("\n")
    println(text)
}
