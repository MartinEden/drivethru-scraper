package eden.drivethru

import eden.drivethru.models.RPGProduct
import eden.drivethru.models.RankedProductGroup
import eden.drivethru.models.Ranking
import eden.drivethru.models.RuleSystem
import kotlinx.serialization.json.Json
import java.nio.file.Path
import kotlin.io.path.writeText

class BestsellersTask(
    private val service: ProductService,
    private val imageService: ImageService,
    private val outputPath: Path
) {
    private val encoder = Json { prettyPrint = true }

    fun run(ranks: Iterable<Ranking>, systems: Iterable<RuleSystem>) {
        val groupedProducts = ranks.map {
            RankedProductGroup(it, getProductsForRanking(it, systems).toSet().toList())
        }
        outputToJson(groupedProducts)
        imageService.downloadImages(groupedProducts)
    }

    private fun outputToJson(groups: List<RankedProductGroup>) {
        val json = encoder.encodeToString(groups)
        outputPath.writeText("const data = $json")
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