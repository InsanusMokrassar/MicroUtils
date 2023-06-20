package dev.inmo.micro_utils.repos.generator.test

import dev.inmo.micro_utils.repos.annotations.GenerateCRUDModel
import dev.inmo.micro_utils.repos.annotations.GenerateCRUDModelExcludeOverride
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@JvmInline
value class TestId(
    val long: Long
)

typealias ParentTypeId = TestId

@GenerateCRUDModel(IRegisteredTest::class)
sealed interface Test {
    val property1: String
    val property2: Int
    @Serializable
    val parent: ParentTypeId?

    @GenerateCRUDModelExcludeOverride
    val excludedProperty: String
        get() = "excluded"
}

sealed interface IRegisteredTest : Test {
    val id: TestId

    @GenerateCRUDModelExcludeOverride
    val excludedProperty2: Boolean
        get() = false
}
