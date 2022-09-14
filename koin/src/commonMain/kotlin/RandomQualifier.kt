package dev.inmo.micro_utils.koin

import com.benasher44.uuid.uuid4
import org.koin.core.qualifier.StringQualifier

fun RandomQualifier(randomFun: () -> String = { uuid4().toString() }) = StringQualifier(randomFun())
