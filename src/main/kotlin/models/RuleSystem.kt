package eden.drivethru.models

data class RuleSystem(val id: String, val name: String) {
    companion object {
        val osr = RuleSystem("45582", "Old-School Revival (OSR)")
        val basic = RuleSystem("44828", "Basic/BECMI or OD&D Titles")
        val adnd1E = RuleSystem("44829", "AD&D 1E")
        val adnd2E = RuleSystem("44830", "AD&D 2E")

        val all = listOf(osr, basic, adnd1E, adnd2E)
    }
}