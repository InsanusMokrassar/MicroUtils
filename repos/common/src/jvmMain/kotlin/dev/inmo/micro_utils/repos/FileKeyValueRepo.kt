package dev.inmo.micro_utils.repos

import dev.inmo.micro_utils.common.Warning
import dev.inmo.micro_utils.common.filename
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

class FileReadKeyValueRepo(
    private val folder: File
) : ReadKeyValueRepo<String, File> {
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
        val filesList = folder.list()
        val files: Array<String> = if (resultPagination.firstIndex < count) {
            val filesPaths = filesList.copyOfRange(resultPagination.firstIndex, resultPagination.lastIndexExclusive.coerceAtMost(filesList.size))

            if (reversed) {
                filesPaths.reversedArray()
            } else {
                filesPaths
            }
        } else {
            emptyArray<String>()
        }
        return files.map { File(folder, it) }.createPaginationResult(
            resultPagination,
            count
        )
    }

    override suspend fun keys(pagination: Pagination, reversed: Boolean): PaginationResult<String> {
        val count = count()
        val resultPagination = if (reversed) pagination.reverse(count) else pagination
        val filesList = folder.list()

        val files: Array<String> = if (resultPagination.firstIndex < count) {
            val filesPaths = filesList.copyOfRange(resultPagination.firstIndex, resultPagination.lastIndexExclusive.coerceAtMost(filesList.size))

            if (reversed) {
                filesPaths.reversedArray()
            } else {
                filesPaths
            }
        } else {
            emptyArray<String>()
        }

        return files.toList().createPaginationResult(
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

    override suspend fun getAll(): Map<String, File> = (folder.listFiles() ?.toList() ?: emptyList()).associateBy { it.filename.name }

    override suspend fun count(): Long = folder.list() ?.size ?.toLong() ?: 0L
}

/**
 * Files watching will not correctly works on Android with version of API lower than API 26
 */
@Warning("Files watching will not correctly works on Android Platform with version of API lower than API 26")
class FileWriteKeyValueRepo(
    private val folder: File,
    filesChangedProcessingScope: CoroutineScope? = null
) : WriteKeyValueRepo<String, File> {
    private val _onNewValue = MutableSharedFlow<Pair<String, File>>()
    override val onNewValue: Flow<Pair<String, File>> = _onNewValue.asSharedFlow()
    private val _onValueRemoved = MutableSharedFlow<String>()
    override val onValueRemoved: Flow<String> = _onValueRemoved.asSharedFlow()

    init {
        if (!folder.mkdirs() && !folder.exists()) {
            error("Unable to create folder ${folder.absolutePath}")
        }
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
        val scope = CoroutineScope(currentCoroutineContext())
        toSet.map { (filename, fileSource) ->
            scope.launch {
                val file = File(folder, filename)

                file.delete()
                fileSource.copyTo(file, overwrite = true)
                if (!file.exists()) {
                    error("Can't create file $file with new content")
                }
                _onNewValue.emit(filename to file)
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

    override suspend fun unsetWithValues(toUnset: List<File>) {
        val keys = toUnset.mapNotNull { v ->
            val key = v.absolutePath.removePrefix(folder.absolutePath)
            if (key != v.absolutePath) {
                key
            } else {
                null
            }
        }
        unset(keys)
    }
}

@Warning("Files watching will not correctly works on Android Platform with version of API lower than API 26")
@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
class FileKeyValueRepo(
    folder: File,
    filesChangedProcessingScope: CoroutineScope? = null
) : KeyValueRepo<String, File>,
    WriteKeyValueRepo<String, File> by FileWriteKeyValueRepo(folder, filesChangedProcessingScope),
    ReadKeyValueRepo<String, File> by FileReadKeyValueRepo(folder) {
}
