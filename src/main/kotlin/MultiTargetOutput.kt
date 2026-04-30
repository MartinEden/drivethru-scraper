package eden.drivethru

import eden.drivethru.models.ViewModel

class MultiTargetOutput(val targets: Iterable<ProductOutput>): ProductOutput {
    constructor(vararg targets: ProductOutput): this(targets.toList())

    override fun write(data: ViewModel) {
        for (target in targets) {
            target.write(data)
        }
    }
}