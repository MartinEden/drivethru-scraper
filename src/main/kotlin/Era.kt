package eden.drivethru

const val WIZARDS_OF_THE_COAST = 44

enum class Category {
    OSR,
    Classic;

    companion object {
        fun fromPublisherId(id: Int) = if (id == WIZARDS_OF_THE_COAST) {
            Category.Classic
        } else {
            Category.OSR
        }
    }
}