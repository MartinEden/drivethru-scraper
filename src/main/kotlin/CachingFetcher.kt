package eden.drivethru

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.io.path.writeBytes
import kotlin.io.path.writeText

data class CachingFetcher(val cacheDirectory: Path, val client: HttpClient) {
    init {
        Files.createDirectories(cacheDirectory)
    }

    fun makeSafeFilename(url: String) = Regex("\\W+").replace(url, "_")

    inline fun <reified T> fetch(url: String): T {
        val filename = makeSafeFilename(url)
        val path = cacheDirectory.resolve(filename)
        return if (path.exists()) {
            Json.Default.decodeFromString(path.readText())
        } else {
            println("Fetching data from $url")
            Thread.sleep(THROTTLE_DELAY)
            val rawData = runBlocking {
                client.get(url).bodyAsText()
            }
            path.writeText(rawData)
            Json.Default.decodeFromString(rawData)
        }
    }

    fun fetchFile(url: String, reason: String? = null): Path {
        val filename = makeSafeFilename(url)
        val path = cacheDirectory.resolve(filename)
        if (!path.exists()) {
            println("Fetching file from $url " + (if (reason != null) "for $reason" else ""))
            Thread.sleep(THROTTLE_DELAY)
            path.writeBytes(runBlocking {
                client.get(url).bodyAsBytes()
            })
        }
        return path
    }

}