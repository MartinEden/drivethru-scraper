package eden.drivethru

import eden.drivethru.models.*
import java.time.LocalDate

class BestsellersTask(
    private val service: ProductService,
    private val imageService: ImageService,
    private val output: ProductOutput
) {
    fun run(ranks: Iterable<Ranking>, systems: Iterable<RuleSystem>) {
        val groupedProducts = ranks.map {
            RankedProductGroup(it, getProductsForRanking(it, systems).toSet().toList())
        }
        output.write(
            ViewModel(
                lastUpdated = LocalDate.now().toString(),
                groups = groupedProducts
            )
        )
        imageService.downloadImages(groupedProducts)
    }

    private fun getProductsForRanking(ranking: Ranking, systems: Iterable<RuleSystem>): Sequence<RPGProduct> =
        sequence {
            println("# $ranking")
            for (ruleSystem in systems) {
                for (product in service.fetch(ranking, ruleSystem)) {
                    println(product)
                    yield(product)
                }
            }
        }
}