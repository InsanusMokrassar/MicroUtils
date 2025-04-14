package dev.inmo.micro_utils.koin

import org.koin.core.Koin
import org.koin.core.scope.Scope

/**
 * Retrieves the first available instance of type [T] from the current scope.
 * This is useful when you need any instance of a type and don't care which one.
 *
 * @param T The type of instance to retrieve
 * @return The first available instance of type [T]
 * @throws NoSuchElementException if no instances of type [T] are available
 */
inline fun <reified T : Any> Scope.getAny() = getAll<T>().first()

/**
 * Retrieves the first available instance of type [T] from the Koin container.
 * This is useful when you need any instance of a type and don't care which one.
 *
 * @param T The type of instance to retrieve
 * @return The first available instance of type [T]
 * @throws NoSuchElementException if no instances of type [T] are available
 */
inline fun <reified T : Any> Koin.getAny() = getAll<T>().first()

