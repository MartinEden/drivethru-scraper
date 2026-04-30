package eden.drivethru.output

import eden.drivethru.models.ViewModel

class MultiTargetOutput(val targets: Iterable<ProductOutputTarget>): ProductOutputTarget {
    constructor(vararg targets: ProductOutputTarget): this(targets.toList())

    override fun write(data: ViewModel) {
        for (target in targets) {
            target.write(data)
        }
    }
}