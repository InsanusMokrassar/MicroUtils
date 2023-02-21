@file:GenerateKoinDefinition("sampleInfo", Test::class, String::class, nullable = false)
package dev.inmo.micro_utils.koin.generator.test

import dev.inmo.micro_utils.koin.annotations.GenerateKoinDefinition
import org.koin.core.Koin

class Test<T>(
    koin: Koin
) {
    init {
        koin.sampleInfo
    }
}
