package eden.drivethru

import gg.jte.ContentType
import gg.jte.TemplateEngine
import gg.jte.output.StringOutput
import gg.jte.resolve.ResourceCodeResolver
import io.ktor.client.*
import java.nio.file.Paths

const val THROTTLE_DELAY = 10 * 1000L
const val CACHE_DIRECTORY = "/home/martin/.drivethru/cache"
const val TEMPLATE_RESOURCE_PATH = "templates"

class BestsellersTask(private val service: ProductService, private val templateEngine: TemplateEngine) {
    fun run(ranks: Iterable<Ranking>, systems: Iterable<RuleSystemReference>) {
        val groupedProducts = ranks.associateWith { getProductsForRanking(it, systems) }

        printToStdout(groupedProducts)

        val output = StringOutput()
        templateEngine.render("template.kte", groupedProducts, output)
        println(output)
    }

    private fun getProductsForRanking(ranking: Ranking, systems: Iterable<RuleSystemReference>): Sequence<RPGItem> =
        sequence {
            for (ruleSystem in systems) {
                yieldAll(service.fetch(ranking, ruleSystem))
            }
        }

    private fun printToStdout(groupedProducts: Map<Ranking, Sequence<RPGItem>>) {
        for ((ranking, products) in groupedProducts) {
            println("# $ranking")
            for (product in products) {
                println(product)
            }
        }
    }
}

fun main() {
    val fetcher = CachingFetcher(Paths.get(CACHE_DIRECTORY), HttpClient())
    val service = ProductService(fetcher)
    val resolver = ResourceCodeResolver(TEMPLATE_RESOURCE_PATH)
    val templateEngine = TemplateEngine.create(resolver, ContentType.Html)
    val task = BestsellersTask(service, templateEngine)

    task.run(
        ranks = listOf(Ranking.Platinum, Ranking.Mithral, Ranking.Adamantine),
        systems = RuleSystemReference.all
    )
}