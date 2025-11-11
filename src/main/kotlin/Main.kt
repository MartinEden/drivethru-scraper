package eden.drivethru

import gg.jte.ContentType
import gg.jte.TemplateEngine
import gg.jte.resolve.ResourceCodeResolver
import io.ktor.client.*
import java.nio.file.Paths

const val THROTTLE_DELAY = 10 * 1000L
const val CACHE_DIRECTORY = "/home/martin/.drivethru/cache"
const val TEMPLATE_RESOURCE_PATH = "templates"

fun main() {
    val fetcher = CachingFetcher(Paths.get(CACHE_DIRECTORY), HttpClient())
    val service = ProductService(fetcher)
    val resolver = ResourceCodeResolver(TEMPLATE_RESOURCE_PATH)
    val templateEngine = TemplateEngine.create(resolver, ContentType.Html)
    val task = BestsellersTask(service, templateEngine)

    task.run(
        ranks = listOf(Ranking.Platinum, Ranking.Mithral, Ranking.Adamantine),
        systems = RuleSystem.all
    )
}