package dev.inmo.micro_utils.handlers.common

import dev.inmo.micro_utils.coroutines.*
import kotlinx.coroutines.*

/**
 * @param layers Will be used in [handle] to iterate over the handler and find layer which have at least one [Handler]
 * to [Handler.handle] its incoming data
 * @param defaultHandler If presented will be used in case where there are no any layer from [layers] with any handler
 * to handle data inside of [handle] method
 */
class HandlersRegistrar<T>(
    private val layers: Iterable<Iterable<Handler<T>>>,
    private val defaultHandler: Handler<T>? = null
) : Handler<T> {
    /**
     * Will iterate over the [layers]. On each layer (in face each [Iterable] of [Handler]s) ALL the handlers will be
     * checked using [Handler.check]. In case if there are no any [Handler] on current layer with true as a result of
     * [Handler.check], [HandlersRegistrar] will take the next level.
     */
    override suspend fun check(data: T): Boolean {
        val scope = CoroutineScope(currentCoroutineContext())
        return layers.any {
            it.map {
                scope.async {
                    safelyWithResult {
                        it.check(data)
                    }.getOrElse { false }
                }
            }.awaitAll().any()
        } || (defaultHandler != null && defaultHandler.check(data))
    }
    /**
     * Will iterate over the [layers].
     *
     * * On each layer (each one is an [Iterable] of [Handler]s) ALL the handlers will be checked using [Handler.check]
     * and all the handlers returned true as a result of [Handler.check] will get call of [Handler.handle] inside of
     * their async coroutine.
     * * In case if there are no any [Handler] on current layer with true as a result of [Handler.check],
     * [HandlersRegistrar] will take the next level.
     * * In case if there are no any [Handler] on all the [layers] with true as a result of [Handler.check],
     * [defaultHandler] will be used (with checking using [Handler.check] as well)
     */
    override suspend fun handle(data: T) {
        val scope = CoroutineScope(currentCoroutineContext())
        val notHandled = layers.none {
            val launchedJobs = it.map {
                scope.async {
                    val launch = safelyWithResult {
                        it.check(data)
                    }.getOrElse { false }
                    if (launch) {
                        scope.launchSafelyWithoutExceptions {
                            it.handle(data)
                        }
                    } else {
                        null
                    }
                }
            }.awaitAll().filterNotNull()

            launchedJobs.joinAll()
            launchedJobs.isNotEmpty()
        }

        if (!notHandled && defaultHandler != null && defaultHandler.check(data)) {
            safelyWithoutExceptions {
                defaultHandler.handle(data)
            }
        }
    }
}
