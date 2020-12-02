package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.*

fun <T> launchSynchronously(scope: CoroutineScope = CoroutineScope(Dispatchers.Default), block: suspend CoroutineScope.() -> T): T {
    var throwable: Throwable? = null
    var result: T? = null
    val objectToSynchronize = java.lang.Object()
    val launchCallback = {
        scope.launch {
            safely(
                {
                    throwable = it
                }
            ) {
                result = block()
            }
            synchronized(objectToSynchronize) {
                objectToSynchronize.notifyAll()
            }
        }
    }
    synchronized(objectToSynchronize) {
        launchCallback()
        objectToSynchronize.wait()
    }
    throw throwable ?: return result!!
}
