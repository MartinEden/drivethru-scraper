package eden.drivethru

import io.ktor.client.*
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.createDirectories
import kotlin.io.path.deleteIfExists
import kotlin.io.path.deleteRecursively

const val THROTTLE_DELAY = 10 * 1000L
const val CACHE_DIRECTORY = "/home/martin/.drivethru/cache"
const val OUTPUT_DIRECTORY = "output"

fun main() {
    val fetcher = CachingFetcher(Paths.get(CACHE_DIRECTORY), HttpClient())
    val service = ProductService(fetcher)

    val outputPath = setupOutputAndGetTargetPath()
    val task = BestsellersTask(service, outputPath)

    task.run(
        ranks = listOf(Ranking.Platinum, Ranking.Mithral, Ranking.Adamantine),
        systems = RuleSystem.all
    )
}

private fun getPathToResource(name: String): Path {
    val resource = BestsellersTask::class.java.getResource(name)
    return resource?.path?.let { Path.of(it) }
        ?: throw Exception("Unable to find resource '$name'")
}

@OptIn(ExperimentalPathApi::class)
private fun setupOutputAndGetTargetPath(): Path {
    val outputDirectory = Path.of(OUTPUT_DIRECTORY)
    outputDirectory.deleteRecursively()
    outputDirectory.createDirectories()
    val htmlPath = getPathToResource("/index.html")
    Files.copy(htmlPath, outputDirectory.resolve("index.html"))

    return outputDirectory.resolve("bestsellers.js")
}