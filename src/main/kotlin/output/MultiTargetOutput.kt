package eden.drivethru.output

import eden.drivethru.models.ViewModel

class MultiTargetOutput(val targets: Iterable<OutputTarget>): OutputTarget {
    constructor(vararg targets: OutputTarget): this(targets.toList())

    override fun write(data: ViewModel) {
        for (target in targets) {
            target.write(data)
        }
    }
}