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
  public override val property1: String,
  public override val property2: Int,
) : Test

@Serializable
@SerialName(value = "RegisteredTest")
public data class RegisteredTest(
  public override val id: TestId,
  public override val property1: String,
  public override val property2: Int,
) : Test, IRegisteredTest

public fun Test.asNew(): NewTest = NewTest(property1, property2)

public fun Test.asRegistered(id: TestId): RegisteredTest = RegisteredTest(id, property1, property2)
