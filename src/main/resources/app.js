export const App = {
    data() {
        return {
            groups: data,
            eras: ["OSR", "Classic"],
            ruleSystems: [...new Set(data.flatMap(g => g.products.flatMap(p => p.ruleSystems)))]
        };
    },
    computed: {
        filteredGroups() {
            const eras = this.eras;
            return this.groups.map(g => ({
                rank: g.rank,
                products: g.products.filter(p => eras.includes(p.era)),
            }));
        }
    },
    methods: {
        toImageUrl(product) {
            return "images/" + product.id;
        },
        toProductUrl(product) {
            return "https://www.drivethrurpg.com/en/product/" + product.id;
        }
    }
};