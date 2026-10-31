import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlin.time.Duration

suspend fun <T> realWithTimeout(time: Duration, block: suspend () -> T): T {
    return withContext(Dispatchers.Default.limitedParallelism(1)) {
        withTimeout(time) {
            block()
        }
    }
}
