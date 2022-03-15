package dev.inmo.micro_utils.ktor.server

import com.benasher44.uuid.uuid4
import dev.inmo.micro_utils.common.FileName
import dev.inmo.micro_utils.common.MPPFile
import dev.inmo.micro_utils.coroutines.launchSafelyWithoutExceptions
import dev.inmo.micro_utils.ktor.common.DefaultTemporalFilesSubPath
import dev.inmo.micro_utils.ktor.common.TemporalFileId
import dev.inmo.micro_utils.ktor.server.configurators.ApplicationRoutingConfigurator
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.PartData
import io.ktor.http.content.streamProvider
import io.ktor.request.receiveMultipart
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.File
import java.nio.file.Files
import java.nio.file.attribute.FileTime

class TemporalFilesRoutingConfigurator(
    private val subpath: String = DefaultTemporalFilesSubPath,
    private val unifiedRouter: UnifiedRouter = UnifiedRouter.default,
    private val temporalFilesUtilizer: TemporalFilesUtilizer = TemporalFilesUtilizer
) : ApplicationRoutingConfigurator.Element {
    interface TemporalFilesUtilizer {
        fun start(filesMap: MutableMap<TemporalFileId, MPPFile>, filesMutex: Mutex, onNewFileFlow: Flow<TemporalFileId>): Job

        companion object : TemporalFilesUtilizer {
            class ByTimerUtilizer(
                private val removeMillis: Long,
                private val scope: CoroutineScope
            ) : TemporalFilesUtilizer {
                override fun start(
                    filesMap: MutableMap<TemporalFileId, MPPFile>,
                    filesMutex: Mutex,
                    onNewFileFlow: Flow<TemporalFileId>
                ): Job = scope.launchSafelyWithoutExceptions {
                    while (isActive) {
                        val filesWithCreationInfo = filesMap.mapNotNull { (fileId, file) ->
                            fileId to ((Files.getAttribute(file.toPath(), "creationTime") as? FileTime) ?.toMillis() ?: return@mapNotNull null)
                        }
                        if (filesWithCreationInfo.isEmpty()) {
                            delay(removeMillis)
                            continue
                        }
                        var min = filesWithCreationInfo.first()
                        for (fileWithCreationInfo in filesWithCreationInfo) {
                            if (fileWithCreationInfo.second < min.second) {
                                min = fileWithCreationInfo
                            }
                        }
                        delay(System.currentTimeMillis() - (min.second + removeMillis))
                        filesMutex.withLock {
                            filesMap.remove(min.first)
                        } ?.delete()
                    }

                }
            }

            override fun start(
                filesMap: MutableMap<TemporalFileId, MPPFile>,
                filesMutex: Mutex,
                onNewFileFlow: Flow<TemporalFileId>
            ): Job = Job()
        }
    }

    private val temporalFilesMap = mutableMapOf<TemporalFileId, MPPFile>()
    private val temporalFilesMutex = Mutex()
    private val filesFlow = MutableSharedFlow<TemporalFileId>()
    val utilizerJob = temporalFilesUtilizer.start(temporalFilesMap, temporalFilesMutex, filesFlow.asSharedFlow())

    override fun Route.invoke() {
        post(subpath) {
            unifiedRouter.apply {
                val multipart = call.receiveMultipart()

                var fileInfo: Pair<TemporalFileId, MPPFile>? = null
                var part = multipart.readPart()

                while (part != null) {
                    if (part is PartData.FileItem) {
                        break
                    }
                    part = multipart.readPart()
                }

                part ?.let {
                    if (it is PartData.FileItem) {
                        val fileId = TemporalFileId(uuid4().toString())
                        val fileName = it.originalFileName ?.let { FileName(it) } ?: return@let
                        fileInfo = fileId to File.createTempFile(fileId.string, ".${fileName.extension}").apply {
                            outputStream().use { outputStream ->
                                it.streamProvider().use {
                                    it.copyTo(outputStream)
                                }
                            }
                            deleteOnExit()
                        }
                    }
                }

                fileInfo ?.also { (fileId, file) ->
                    temporalFilesMutex.withLock {
                        temporalFilesMap[fileId] = file
                    }
                    call.respond(fileId.string)
                    launchSafelyWithoutExceptions { filesFlow.emit(fileId) }
                } ?: call.respond(HttpStatusCode.BadRequest)
            }
        }
    }

    suspend fun removeTemporalFile(temporalFileId: TemporalFileId) {
        temporalFilesMutex.withLock {
            temporalFilesMap.remove(temporalFileId)
        }
    }

    fun getTemporalFile(temporalFileId: TemporalFileId) = temporalFilesMap[temporalFileId]

    suspend fun getAndRemoveTemporalFile(temporalFileId: TemporalFileId) = temporalFilesMutex.withLock {
        temporalFilesMap.remove(temporalFileId)
    }
}
