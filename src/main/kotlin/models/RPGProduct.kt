package eden.drivethru.models

import eden.drivethru.Product
import kotlinx.serialization.Serializable

@Serializable
data class RPGProduct(
    val id: Int,
    val name: String,
    val ranking: Ranking,
    val imageUrl: String?,
    val ruleSystems: List<String>,
    val era: Era,
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
                },
                era = Era.fromPublisherId(product.attributes.publisherId)
            )
        }
    }
}
