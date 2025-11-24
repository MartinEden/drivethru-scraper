@file:OptIn(ExperimentalSerializationApi::class)

package eden.drivethru

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@Serializable
@JsonIgnoreUnknownKeys
data class MetaInfo(
    val totalItems: Int,
    val itemsPerPage: Int,
    val currentPage: Int
)

@Serializable
@JsonIgnoreUnknownKeys
data class RankingInfo(val humanName: String)

@Serializable
@JsonIgnoreUnknownKeys
data class ProductDescription(val name: String)

@Serializable
@JsonIgnoreUnknownKeys
data class FilterDescription(
    val name: String,
    val slug: String,
    val languageISOCode: String,    // Do not use - contain bad data
    val languageCode: String,
    val languageName: String
)

@Serializable
@JsonIgnoreUnknownKeys
data class FilterValue(
    val filterId: Int,
    val ancestors: List<Int>,
    val descriptions: List<FilterDescription>
)

@Serializable
@JsonIgnoreUnknownKeys
data class ProductAttributes(
    val productId: Int,
    val description: ProductDescription,
    val ranking: RankingInfo? = null,
    val storefrontPrimaryFilterValues: List<FilterValue>,
    val thumbnail200: String? = null,
    val publisherId: Int
)

@Serializable
@JsonIgnoreUnknownKeys
data class Product(
    val attributes: ProductAttributes
)

@Serializable
@JsonIgnoreUnknownKeys
data class ProductResponse(
    val meta: MetaInfo,
    val data: List<Product>,
)