package eden.drivethru

import eden.drivethru.models.*
import eden.drivethru.output.OutputTarget
import java.time.LocalDate

class BestsellersTask(
    private val service: ProductService,
    private val imageService: ImageService,
    private val output: OutputTarget
) {
    fun run(ranks: Iterable<Ranking>, systems: Iterable<RuleSystem>) {
        val groupedProducts = ranks.map { rank ->
            val products = getProductsForRanking(rank, systems)
                .toSet()        // Remove duplicates
                .sortedWith(compareBy({ it.era }, { it.name }))
                .toList()
            RankedProductGroup(rank, products)
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