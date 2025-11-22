package eden.drivethru

import kotlinx.serialization.Serializable

@Serializable
data class RPGProduct(
    val id: Int,
    val name: String,
    val ranking: Ranking,
    val imageUrl: String?,
    val ruleSystems: List<String>
) {
    companion object {
        fun fromJsonData(product: Product): RPGProduct {
            return RPGProduct(
                id = product.attributes.productId,
                name = product.attributes.description.name,
                ranking = Ranking.fromString(product.attributes.ranking?.humanName),
                imageUrl = product.attributes.thumbnail200,
                ruleSystems = product.attributes.storefrontPrimaryFilterValues.map { fv ->
                    fv.descriptions.firstOrNull { it.languageCode == "en" }?.name ?: "Unknown"
                }
            )
        }
    }
}

fun Map<Ranking, Sequence<RPGProduct>>.reify() =
    this.entries.associate { (ranking, collection) -> ranking to collection.toList() }