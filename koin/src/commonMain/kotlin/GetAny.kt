package dev.inmo.micro_utils.koin

import org.koin.core.Koin
import org.koin.core.scope.Scope

inline fun <reified T : Any> Scope.getAny() = getAll<T>().first()
inline fun <reified T : Any> Koin.getAny() = getAll<T>().first()

