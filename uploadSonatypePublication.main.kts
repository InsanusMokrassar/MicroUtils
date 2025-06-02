#!/usr/bin/env kotlin

import java.net.http.HttpRequest.BodyPublisher
import java.nio.charset.StandardCharsets
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.net.URI
import kotlin.io.encoding.Base64

@OptIn(kotlin.io.encoding.ExperimentalEncodingApi::class)
fun uploadSonatypePublication (repos_state: String, publishing_type: String) {
    val bearer = let {
        val username = System.getenv("SONATYPE_USER")
        val password = System.getenv("SONATYPE_PASSWORD")
        Base64.encodeToByteArray("$username:$password".encodeToByteArray())
    }
    val baseUrl = "https://ossrh-staging-api.central.sonatype.com/manual"
    val client = HttpClient.newHttpClient()
    val makeRequest: (String, BodyPublisher, String) -> HttpResponse<String> = { method, bodyPublisher, suffix ->
        val request = HttpRequest.newBuilder()
            .uri(URI.create("$baseUrl/$suffix"))
            .method(method, bodyPublisher)
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer $bearer")
            .build()
        client.send(request, HttpResponse.BodyHandlers.ofString())
    }

    val response = makeRequest(
            "GET",
        HttpRequest.BodyPublishers.ofString(""),
        "/search/repositories?state=$repos_state"
    )
    val keys = mutableListOf<String>()
    Regex("\"key\"[\\s]*:[\\s]*\"[^\"]+\"").findAll(
        response.body()
    ).forEach {
        val key = Regex("[^\"]+").find(Regex("[^\"]+\"\$").find(it.value) ?.value ?: return@forEach) ?.value ?: return@forEach
        keys.add(key)
    }
    keys.forEach {
        println("Start uploading $it")
        val uploadResponse = makeRequest(
            "POST",
            HttpRequest.BodyPublishers.ofString(""),
            "upload/repository/$it?publishing_type=$publishing_type"
        )
        if (uploadResponse.statusCode() != 200) {
            println("Faced error of uploading for repo with key $it. Response: $uploadResponse")
        }
    }
}

val repos_state = runCatching { System.getenv("repos_state").takeIf { it.isNotEmpty() } }.getOrNull() ?: "open"
val publishing_type = runCatching { System.getenv("publishing_type").takeIf { it.isNotEmpty() } }.getOrNull() ?: "user_managed"

uploadSonatypePublication(repos_state, publishing_type)
