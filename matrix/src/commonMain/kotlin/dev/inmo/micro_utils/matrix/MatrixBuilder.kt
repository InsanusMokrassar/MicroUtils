package dev.inmo.micro_utils.matrix

class MatrixBuilder<T> {
    private val mutMatrix: MutableList<List<T>> = ArrayList()
    val matrix: Matrix<T>
        get() = mutMatrix

    fun add(t: List<T>) = mutMatrix.add(t)
    operator fun plus(t: List<T>) = add(t)
}
