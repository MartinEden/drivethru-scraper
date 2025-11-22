package eden.drivethru

import java.nio.file.Path
import kotlin.io.path.copyTo

const val IMAGE_BASE_URL = "https://d1vzi28wh99zvq.cloudfront.net/images/"

class ImageService(private val cachingFetcher: CachingFetcher, output: Path) {
    private val output = output.resolve("images")

    fun downloadImages(groups: List<RankedProductGroup>) {
        for (group in groups) {
            for (product in group.products) {
                downloadProductImage(product)
            }
        }
    }

    fun downloadProductImage(product: RPGProduct) {
        val url = IMAGE_BASE_URL + product.imageUrl
        val tempPath = cachingFetcher.fetchFile(url, reason = product.name)
        val newFileName = product.id.toString()
        tempPath.copyTo(output.resolve(newFileName), overwrite = true)
    }
}