package eden.drivethru

import eden.drivethru.models.ViewModel

interface ProductOutput {
    fun write(data: ViewModel)
}