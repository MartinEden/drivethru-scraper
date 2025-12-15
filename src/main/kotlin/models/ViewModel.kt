package eden.drivethru.models

import kotlinx.serialization.Serializable

@Serializable
data class ViewModel(
    val lastUpdated: String,
    val groups: List<RankedProductGroup>,
)
