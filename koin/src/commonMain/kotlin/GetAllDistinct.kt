package dev.inmo.micro_utils.koin

import org.koin.core.Koin
import org.koin.core.scope.Scope

/**
 * Retrieves all instances of type [T] from the current [Scope] and returns them as a distinct list.
 * This function is useful when you want to avoid duplicate instances of the same type.
 *
 * @param T The type of instances to retrieve
 * @return A list of distinct instances of type [T]
 */
inline fun <reified T : Any> Scope.getAllDistinct() = getAll<T>().distinct()

/**
 * Retrieves all instances of type [T] from the [Koin] container and returns them as a distinct list.
 * This function is useful when you want to avoid duplicate instances of the same type.
 *
 * @param T The type of instances to retrieve
 * @return A list of distinct instances of type [T]
 */
inline fun <reified T : Any> Koin.getAllDistinct() = getAll<T>().distinct()

