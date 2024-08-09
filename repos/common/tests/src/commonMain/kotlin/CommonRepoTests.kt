package dev.inmo.micro_utils.repos.common.tests

abstract class CommonRepoTests<T> {
    protected open val testSequencesSize = 1000
    protected abstract val repoCreator: suspend () -> T
}