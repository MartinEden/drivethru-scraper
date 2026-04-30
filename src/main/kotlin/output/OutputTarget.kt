package eden.drivethru.output

import eden.drivethru.models.ViewModel

interface ProductOutputTarget {
    fun write(data: ViewModel)
}