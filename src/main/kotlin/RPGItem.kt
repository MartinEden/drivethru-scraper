package eden.drivethru

data class RPGItem(
    val id: Int,
    val name: String,
    val ranking: Ranking,
    val ruleSystems: List<String>
) {
    companion object {
        fun fromJsonData(product: Product): RPGItem {
            return RPGItem(
                id = product.attributes.productId,
                name = product.attributes.description.name,
                ranking = Ranking.fromString(product.attributes.ranking?.humanName),
                ruleSystems = product.attributes.storefrontPrimaryFilterValues.map { fv ->
                    fv.descriptions.firstOrNull { it.languageCode == "en" }?.name ?: "Unknown"
                }
            )
        }
    }
}