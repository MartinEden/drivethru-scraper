package eden.drivethru

import io.ktor.client.*
import java.nio.file.Paths

const val THROTTLE_DELAY = 10 * 1000L
const val CACHE_DIRECTORY = "/home/martin/.drivethru/cache"

fun main() {
    val fetcher = CachingFetcher(Paths.get(CACHE_DIRECTORY), HttpClient())
    val service = ProductService(fetcher)

    val relevantRanks = listOf(Ranking.Adamantine, Ranking.Mithral, Ranking.Platinum)
    val groupedProducts = relevantRanks.associateWith { getAllProductsForRanking(it, service) }

    for ((ranking, products) in groupedProducts) {
        println("# $ranking")
        for (product in products) {
            println(product)
        }
    }
}

fun getAllProductsForRanking(ranking: Ranking, service: ProductService): Sequence<RPGItem> = sequence {
    for (ruleSystem in RuleSystemReference.all) {
        yieldAll(service.fetch(ranking, ruleSystem))
    }
}