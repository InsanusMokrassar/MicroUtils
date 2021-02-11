package dev.inmo.micro_utils.matrix

class RowBuilder<T> {
    private val mutRow: MutableList<T> = ArrayList()
    val row: Row<T>
        get() = mutRow

    fun add(t: T) = mutRow.add(t)
    operator fun T.unaryPlus() = add(this)
}
