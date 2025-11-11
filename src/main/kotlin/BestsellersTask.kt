package eden.drivethru

import gg.jte.TemplateEngine
import gg.jte.output.StringOutput
import kotlin.collections.iterator

class BestsellersTask(private val service: ProductService, private val templateEngine: TemplateEngine) {
    fun run(ranks: Iterable<Ranking>, systems: Iterable<RuleSystem>) {
        val groupedProducts = ranks.associateWith { getProductsForRanking(it, systems) }

        printToStdout(groupedProducts)

        val output = StringOutput()
        templateEngine.render("template.kte", groupedProducts, output)
        println(output)
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