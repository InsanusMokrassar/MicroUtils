package dev.inmo.micro_utils.matrix

open class MatrixBuilder<T> {
    private val mutMatrix: MutableList<List<T>> = ArrayList()
    val matrix: Matrix<T>
        get() = mutMatrix

    fun row(t: List<T>) = mutMatrix.add(t)
    fun add(t: List<T>) = mutMatrix.add(t)
    operator fun List<T>.unaryPlus() = row(this)
    operator fun plus(t: List<T>) = add(t)
    operator fun T.unaryPlus() = add(listOf(this))
}

fun <T> MatrixBuilder<T>.row(block: RowBuilder<T>.() -> Unit) = +RowBuilder<T>().also(block).row
fun <T> MatrixBuilder<T>.row(vararg elements: T) = +elements.toList()
