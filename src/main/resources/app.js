export const App = {
    data() {
        const allSystems = [...new Set(data.groups.flatMap(g => g.products.flatMap(p => p.ruleSystems)))];

        return {
            groups: data.groups,
            lastUpdated: data.lastUpdated,
            eras: ["OSR", "Classic"],
            ruleSystems: allSystems.map(s => ({ name: s, state: 0 })),
            showControls: false,
            showExplanation: false,
            tagStates: [
                { value: -1, name: "Must not be compatible with" },
                { value: 0, name: "No preference" },
                { value: 1, name: "Must be compatible with"},
            ]
        };
    },
    computed: {
        filteredGroups() {
            return this.groups.map(g => ({
                rank: g.rank,
                products: g.products.filter(p => this.shouldDisplay(p)),
            }));
        },
        totalProducts() {
            return this.filteredGroups.reduce((sum, g) => sum + g.products.length, 0);
        },
        controlsActionWord() {
            if (this.showControls) {
                return "Hide";
            } else {
                return "Show";
            }
        }
    },
    methods: {
        toImageUrl(product) {
            return "images/" + product.id;
        },
        toProductUrl(product) {
            return "https://www.drivethrurpg.com/en/product/" + product.id;
        },
        shouldDisplay(product) {
            return this.eras.includes(product.era)
                && this.ruleSystems.every(s => this.obeysRuleSystemRule(product, s));
        },
        obeysRuleSystemRule(product, system) {
            return system.state == 0
                || (system.state == 1 && product.ruleSystems.includes(system.name))
                || (system.state == -1 && !product.ruleSystems.includes(system.name))
        },
        totalForRank(rank) {
            console.log(rank);
            console.log(this.filteredGroups);
            return this.filteredGroups
                .find(g => g.rank == rank)
                .products.length;
        },
        toggleRuleSystemState(system) {
            system.state = system.state + 1;
            if (system.state > 1) {
                system.state = -1
            }
        },
        toggleControls() {
            this.showControls = !this.showControls;
        },
        toggleExplanation() {
            this.showExplanation = !this.showExplanation;
        }
    }
};