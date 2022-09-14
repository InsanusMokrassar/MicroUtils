package dev.inmo.micro_utils.koin

import org.koin.core.Koin
import org.koin.core.scope.Scope

inline fun <reified T : Any> Scope.getAllDistinct() = getAll<T>().distinct()
inline fun <reified T : Any> Koin.getAllDistinct() = getAll<T>().distinct()

