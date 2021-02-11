package dev.inmo.micro_utils.matrix

fun <T> matrix(block: MatrixBuilder<T>.() -> Unit): Matrix<T> {
    return MatrixBuilder<T>().also(block).matrix
}

fun <T> flatMatrix(block: RowBuilder<T>.() -> Unit): Matrix<T> {
    return MatrixBuilder<T>().apply {
        row(block)
    }.matrix
}

fun <T> flatMatrix(vararg elements: T): Matrix<T> {
    return MatrixBuilder<T>().apply {
        row { elements.forEach { +it } }
    }.matrix
}
