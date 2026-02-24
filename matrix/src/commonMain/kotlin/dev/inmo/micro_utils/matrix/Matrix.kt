package dev.inmo.micro_utils.matrix

/**
 * Represents a matrix as a list of rows, where each row is a list of elements.
 * This is essentially a 2D structure represented as `List<List<T>>`.
 *
 * @param T The type of elements in the matrix
 */
typealias Matrix<T> = List<Row<T>>
