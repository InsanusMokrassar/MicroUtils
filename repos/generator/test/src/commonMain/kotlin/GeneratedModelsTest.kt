// THIS CODE HAVE BEEN GENERATED AUTOMATICALLY
// TO REGENERATE IT JUST DELETE FILE
// ORIGINAL FILE: Test.kt
package dev.inmo.micro_utils.repos.generator.test

import kotlin.Int
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName(value = "NewTest")
public data class NewTest(
  override val property1: String,
  override val property2: Int,
  @Serializable
  @SerialName(`value` = "custom_parent_name")
  override val parent: ParentTypeId?,
) : Test

@Serializable
@SerialName(value = "RegisteredTest")
public data class RegisteredTest(
  override val id: TestId,
  override val property1: String,
  override val property2: Int,
  @Serializable
  @SerialName(`value` = "custom_parent_name")
  override val parent: ParentTypeId?,
) : Test, IRegisteredTest

public fun Test.asNew(): NewTest = NewTest(property1, property2, parent)

public fun Test.asRegistered(id: TestId): RegisteredTest = RegisteredTest(id, property1, property2,
    parent)
