package eden.drivethru

import kotlinx.serialization.Serializable

@Serializable
data class RankedProductGroup(
    val rank: Ranking,
    val products: List<RPGProduct>
)