package dev.inmo.micro_utils.koin

import com.benasher44.uuid.uuid4
import org.koin.core.qualifier.StringQualifier

/**
 * Creates a [StringQualifier] with a random string value.
 *
 * @param randomFun A function that generates a random string. By default, it uses UUID4 string representation.
 * @return A [StringQualifier] with a randomly generated string value
 */
fun RandomQualifier(randomFun: () -> String = { uuid4().toString() }) = StringQualifier(randomFun())
