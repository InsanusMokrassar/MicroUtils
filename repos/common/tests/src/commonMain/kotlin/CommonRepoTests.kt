import dev.inmo.micro_utils.repos.CRUDRepo

abstract class CommonRepoTests<T> {
    protected open val testSequencesSize = 1000
    protected abstract val repoCreator: suspend () -> T
}