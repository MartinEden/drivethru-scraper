package eden.drivethru

const val ADVENTURES_AND_SUPPLEMENTS = "2110"
const val API_ENDPOINT = "https://api.drivethrurpg.com/api/vBeta/products"

class ProductService(private val fetcher: CachingFetcher) {
    private val baseQueryParameters = mapOf(
        "groupId" to 1,
        "order[description.name]" to "asc",
        "status" to 1,
        "partial" to "false"
    )

    private fun getProducts(ranking: Ranking, page: Int, ruleSystem: RuleSystem): ProductResponse {
        val extraParameters = mapOf(
            "page" to page,
            "ranking" to ranking.name.lowercase(),
            "filters.filterId[require]" to "${ruleSystem.id},$ADVENTURES_AND_SUPPLEMENTS",
        )
        val parameters = (baseQueryParameters + extraParameters)
        val queryString = parameters.entries.sortedBy { it.key }.joinToString("&") { (k, v) -> "$k=$v" }
        val url = "$API_ENDPOINT?$queryString"
        return fetcher.fetch(url)
    }

    fun fetch(ranking: Ranking, ruleSystem: RuleSystem): Sequence<RPGProduct> {
        return sequence {
            var shouldContinue = true
            var page = 1
            while (shouldContinue) {
                val response = getProducts(ranking, page, ruleSystem)
                shouldContinue = response.meta.totalItems > response.meta.itemsPerPage * page
                val products = response.data.map { RPGProduct.fromJsonData(it) }
                if (products.any()) {
                    yieldAll(products)
                    page += 1
                }
            }
        }
    }
}