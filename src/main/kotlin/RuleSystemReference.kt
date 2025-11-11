package eden.drivethru

data class RuleSystemReference(val id: String, val name: String) {
    companion object {
        val osr = RuleSystemReference("45582", "Old-School Revival (OSR)")
        val basic = RuleSystemReference("44828", "Basic/BECMI or OD&D Titles")
        val adnd1E = RuleSystemReference("44829", "AD&D 1E")
        val adnd2E = RuleSystemReference("44830", "AD&D 2E")

        val all = listOf(osr, basic, adnd1E, adnd2E)
    }
}
