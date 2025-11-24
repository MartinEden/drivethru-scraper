package eden.drivethru.models

const val WIZARDS_OF_THE_COAST = 44

enum class Era {
    OSR,
    Classic;

    companion object {
        fun fromPublisherId(id: Int) = if (id == WIZARDS_OF_THE_COAST) {
            Classic
        } else {
            OSR
        }
    }
}