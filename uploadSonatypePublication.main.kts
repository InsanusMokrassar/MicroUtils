#!/usr/bin/env kotlin

@file:Repository("https://repo.maven.apache.org/maven2/")
@file:DependsOn("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.1")

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import java.net.http.HttpRequest.BodyPublisher
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.net.URI

class CentralSonatypeOSSRHApi(
    private val bearer: String,
) {
    data class SonatypeOSSRHRepository(
        val key: String,
        val portalDeploymentId: String?,
        val state: String,
    )

    private val baseUrl = "https://ossrh-staging-api.central.sonatype.com/manual"
    private val client = HttpClient.newHttpClient()
    private fun makeRequest(
        method: String,
        suffix: String,
        bodyPublisher: BodyPublisher = HttpRequest.BodyPublishers.ofString("")
    ): JsonObject? {
        val request = HttpRequest.newBuilder()
            .uri(URI.create("$baseUrl/$suffix"))
            .method(method, bodyPublisher)
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer $bearer")
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        println(response)
        val responseBody = response.body()
        println(responseBody)
        return when {
            response.statusCode() == 200 && responseBody.isNotEmpty() -> {
                return Json.decodeFromString(JsonObject.serializer(), responseBody)
            }
            response.statusCode() == 200 -> {
                return JsonObject(emptyMap())
            }
            else -> null
        }
    }

    fun repositories(
        profileId: String? = null,
        state: String? = null,
        ip: String? = null,
    ): List<SonatypeOSSRHRepository>? {
        val repositories = makeRequest(
            "GET",
            "search/repositories?${state ?.let { "state=$it" } ?: ""}${profileId ?.let { "&profile_id=$it" } ?: ""}${ip ?.let { "&ip=$it" } ?: ""}"
        ) ?.get("repositories") as? JsonArray

        return repositories ?.mapNotNull {
            val asObject = it as? JsonObject ?:return@mapNotNull null
            SonatypeOSSRHRepository(
                (asObject["key"] as JsonPrimitive).content,
                (asObject["portal_deployment_id"] as? JsonPrimitive) ?.content,
                (asObject["state"] as JsonPrimitive).content,
            )
        }
    }

    fun drop(repositoryKey: String, ): Boolean {
        val dropped = makeRequest(
            "DELETE",
            "drop/repository/$repositoryKey"
        )

        return dropped != null
    }

    fun uploadDefault(requestedNamespace: String, publishingType: String? = null): Boolean {
        val uploaded = makeRequest(
            "POST",
            "upload/defaultRepository/$requestedNamespace?${publishingType ?.let { "publishing_type=$publishingType" } ?: ""}"
        )

        return uploaded != null
    }

    fun upload(repositoryKey: String, publishingType: String? = null): Boolean {
        val uploaded = makeRequest(
            "POST",
            "upload/repository/$repositoryKey?${publishingType ?.let { "publishing_type=$publishingType" } ?: ""}"
        )

        return uploaded != null
    }
}

val repos_state = runCatching { System.getenv("repos_state").takeIf { it.isNotEmpty() } }.getOrNull() ?: "open"
val publishing_type = runCatching { System.getenv("publishing_type").takeIf { it.isNotEmpty() } }.getOrNull() ?: "user_managed"
val api = CentralSonatypeOSSRHApi(
    let {
        val username = System.getenv("SONATYPE_USER")
        val password = System.getenv("SONATYPE_PASSWORD")
        java.util.Base64.getEncoder().encodeToString("$username:$password".encodeToByteArray())
    }
)

api.repositories() ?.forEach {
    if (it.state == "open") {
        println("Start uploading of ${it.key}")
        val uploaded = api.upload(it.key, "user_managed")

        println("Complete uploading of ${it.key}. Status ok: $uploaded")
    }
}
api.repositories() ?.forEach {
    if (it.state == "closed") {
        println("Start uploading of ${it.key} with auto mode ")
        val uploaded = api.upload(it.key, "automatic")

        println("Complete uploading of ${it.key} with auto mode . Status ok: $uploaded")
    }
}
