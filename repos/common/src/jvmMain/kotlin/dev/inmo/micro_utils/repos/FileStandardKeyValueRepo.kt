package dev.inmo.micro_utils.repos

import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.pagination.utils.reverse
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.io.File
import java.nio.file.FileSystems
import java.nio.file.Path
import java.nio.file.StandardWatchEventKinds.*

private inline val String.isAbsolute
    get() = startsWith(File.separator)

class FileReadStandardKeyValueRepo(
    private val folder: File
) : ReadStandardKeyValueRepo<String, File> {
    init {
        folder.mkdirs()
    }

    override suspend fun get(k: String): File? {
        val file = File(folder, k)
        if (file.exists()) {
            return file
        }
        return null
    }

    override suspend fun values(pagination: Pagination, reversed: Boolean): PaginationResult<File> {
        val count = count()
        val resultPagination = if (reversed) pagination.reverse(count) else pagination
        val filesPaths = folder.list() ?.copyOfRange(resultPagination.firstIndex, resultPagination.lastIndex) ?: return emptyPaginationResult()
        if (reversed) {
            filesPaths.reverse()
        }
        return filesPaths.map { File(folder, it) }.createPaginationResult(
            resultPagination,
            count
        )
    }

    override suspend fun keys(pagination: Pagination, reversed: Boolean): PaginationResult<String> {
        val count = count()
        val resultPagination = if (reversed) pagination.reverse(count) else pagination
        val filesPaths = folder.list() ?.copyOfRange(resultPagination.firstIndex, resultPagination.lastIndex) ?: return emptyPaginationResult()
        if (reversed) {
            filesPaths.reverse()
        }
        return filesPaths.toList().createPaginationResult(
            resultPagination,
            count
        )
    }

    override suspend fun keys(
        v: File,
        pagination: Pagination,
        reversed: Boolean
    ): PaginationResult<String> {
        val resultPagination = if (reversed) pagination.reverse(1L) else pagination
        return if (resultPagination.isFirstPage) {
            val fileSubpath = v.absolutePath.removePrefix(folder.absolutePath)
            if (fileSubpath == v.absolutePath) {
                emptyList()
            } else {
                listOf(fileSubpath)
            }
        } else {
            emptyList()
        }.createPaginationResult(resultPagination, 1L)
    }

    override suspend fun contains(key: String): Boolean {
        return File(folder, key).exists()
    }

    override suspend fun count(): Long = folder.list() ?.size ?.toLong() ?: 0L
}

/**
 * Files watching will not correctly works on Android with version of API lower than API 26
 */
class FileWriteStandardKeyValueRepo(
    private val folder: File,
    filesChangedProcessingScope: CoroutineScope? = null
) : WriteStandardKeyValueRepo<String, File> {
    private val _onNewValue = MutableSharedFlow<Pair<String, File>>()
    override val onNewValue: Flow<Pair<String, File>> = _onNewValue.asSharedFlow()
    private val _onValueRemoved = MutableSharedFlow<String>()
    override val onValueRemoved: Flow<String> = _onValueRemoved.asSharedFlow()

    init {
        folder.mkdirs()
        filesChangedProcessingScope ?.let {
            it.launch {
                try {
                    val folderPath = folder.toPath()
                    while (isActive) {
                        val key = try {
                            folderPath.register(FileSystems.getDefault().newWatchService(), ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE)
                        } catch (e: Exception) {
                            // add verbose way to show that file watching is not working
                            return@launch
                        }

                        for (event in key.pollEvents()) {
                            val relativeFilePath = (event.context() as? Path) ?: continue
                            val file = relativeFilePath.toFile()
                            val relativePath = file.toRelativeString(folder)

                            when (event.kind()) {
                                ENTRY_CREATE, ENTRY_MODIFY -> {
                                    launch {
                                        _onNewValue.emit(relativePath to file)
                                    }
                                }
                                ENTRY_DELETE -> {
                                    launch {
                                        _onValueRemoved.emit(relativePath)
                                    }
                                }
                            }
                        }

                        if (key.isValid || folder.exists()) {
                            continue
                        }
                        break
                    }
                } catch (e: Throwable) {
                    // add verbose way to notify that this functionality is disabled
                }
            }
        }
    }

    override suspend fun set(toSet: Map<String, File>) {
        supervisorScope {
            toSet.map { (filename, fileSource) ->
                launch {
                    val file = File(folder, filename)

                    file.delete()
                    fileSource.copyTo(file, overwrite = true)
                    _onNewValue.emit(filename to file)
                }
            }
        }.joinAll()
    }

    override suspend fun unset(toUnset: List<String>) {
        toUnset.forEach {
            val file = File(folder, it)
            if (file.exists()) {
                file.delete()
                _onValueRemoved.emit(it)
            }
        }
    }
}

class FileStandardKeyValueRepo(
    folder: File,
    filesChangedProcessingScope: CoroutineScope? = null
) : StandardKeyValueRepo<String, File>,
    WriteStandardKeyValueRepo<String, File> by FileWriteStandardKeyValueRepo(folder, filesChangedProcessingScope),
    ReadStandardKeyValueRepo<String, File> by FileReadStandardKeyValueRepo(folder)