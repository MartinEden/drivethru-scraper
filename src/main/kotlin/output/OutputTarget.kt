package eden.drivethru.output

import eden.drivethru.models.ViewModel

interface OutputTarget {
    fun write(data: ViewModel)
}