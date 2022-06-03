import com.benasher44.uuid.uuid4
import kotlinx.serialization.Serializable

@Serializable
data class ComplexData(
    val id: Int,
    val simple: SimpleData = SimpleData(),
    val simples: List<SimpleData> = (0 until 100).map { SimpleData(("$it")) },
    val title: String = uuid4().toString()
)
