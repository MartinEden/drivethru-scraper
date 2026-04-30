package eden.drivethru

import eden.drivethru.models.Ranking
import eden.drivethru.models.RuleSystem
import io.ktor.client.*
import java.nio.file.Path
import java.nio.file.Paths

const val THROTTLE_DELAY = 10 * 1000L
const val CACHE_DIRECTORY = "/home/martin/.drivethru/cache"
const val OUTPUT_DIRECTORY = "output"

fun main() {
    val outputDirectory = Path.of(OUTPUT_DIRECTORY)
    val fetcher = CachingFetcher(Paths.get(CACHE_DIRECTORY), HttpClient())
    val service = ProductService(fetcher)
    val imageService = ImageService(fetcher, outputDirectory)

    val output = MultiTargetOutput(
        JsonOutput(
            outputDirectory.resolve("bestsellers.js")
        )
    )
    val task = BestsellersTask(service, imageService, output)

    task.run(
        ranks = listOf(Ranking.Adamantine, Ranking.Mithral, Ranking.Platinum),
        systems = RuleSystem.all
    )
}
