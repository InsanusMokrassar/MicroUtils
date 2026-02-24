package dev.inmo.micro_utils.matrix

/**
 * Creates a matrix using a DSL-style builder.
 * Allows defining multiple rows using the [MatrixBuilder] API.
 *
 * @param T The type of elements in the matrix
 * @param block A builder lambda to define the matrix structure
 * @return A constructed [Matrix]
 */
fun <T> matrix(block: MatrixBuilder<T>.() -> Unit): Matrix<T> {
    return MatrixBuilder<T>().also(block).matrix
}

/**
 * Creates a single-row matrix using a DSL-style builder.
 *
 * @param T The type of elements in the matrix
 * @param block A builder lambda to define the row elements
 * @return A [Matrix] containing a single row
 */
fun <T> flatMatrix(block: RowBuilder<T>.() -> Unit): Matrix<T> {
    return MatrixBuilder<T>().apply {
        row(block)
    }.matrix
}

/**
 * Creates a single-row matrix from the provided elements.
 *
 * @param T The type of elements in the matrix
 * @param elements The elements to include in the single row
 * @return A [Matrix] containing a single row with the specified elements
 */
fun <T> flatMatrix(vararg elements: T): Matrix<T> {
    return MatrixBuilder<T>().apply {
        row { elements.forEach { +it } }
    }.matrix
}
