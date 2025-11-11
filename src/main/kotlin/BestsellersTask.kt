package eden.drivethru

import kotlinx.serialization.json.Json
import java.nio.file.Path
import kotlin.io.path.writeText

class BestsellersTask(private val service: ProductService, private val outputPath: Path) {
    private val encoder = Json { prettyPrint = true }

    fun run(ranks: Iterable<Ranking>, systems: Iterable<RuleSystem>) {
        val groupedProducts = ranks.associateWith { getProductsForRanking(it, systems) }

        printToStdout(groupedProducts)
        val json = encoder.encodeToString(groupedProducts.reify())
        outputPath.writeText("const data = $json")
    }

    private fun getProductsForRanking(ranking: Ranking, systems: Iterable<RuleSystem>): Sequence<RPGItem> =
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