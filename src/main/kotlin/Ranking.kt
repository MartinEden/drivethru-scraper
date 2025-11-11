package eden.drivethru

import kotlinx.serialization.Serializable

@Serializable
enum class Ranking {
    None,
    Copper,
    Silver,
    Electrum,
    Gold,
    Platinum,
    Mithral,
    Adamantine;

    companion object {
        fun fromString(raw: String?) = entries.singleOrNull { it.name.lowercase() == raw } ?: None
    }
}