package dev.inmo.micro_utils.matrix

class RowBuilder<T> {
    private val mutRow: MutableList<T> = ArrayList()
    val row: Row<T>
        get() = mutRow

    fun column(t: T) = mutRow.add(t)
    operator fun T.unaryPlus() = column(this)
}

fun <T> row(block: RowBuilder<T>.() -> Unit): List<T> = RowBuilder<T>().also(block).row
fun <T> RowBuilder<T>.columns(elements: List<T>) = elements.forEach(::column)
fun <T> RowBuilder<T>.columns(vararg elements: T) = elements.forEach(::column)
