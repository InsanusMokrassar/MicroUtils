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

fun <T> row(block: RowBuilder<T>.() -> Unit): List<T> {
    return RowBuilder<T>().also(block).row
}

fun <T> MatrixBuilder<T>.row(block: RowBuilder<T>.() -> Unit) {
    add(RowBuilder<T>().also(block).row)
}

fun <T> MatrixBuilder<T>.row(vararg elements: T) {
    add(elements.toList())
}

operator fun <T> RowBuilder<T>.plus(t: T) = add(t)
