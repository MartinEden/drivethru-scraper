package eden.drivethru

const val IMAGE_BASE_URL = "https://d1vzi28wh99zvq.cloudfront.net/images/"

class ImageService(private val cachingFetcher: CachingFetcher) {

    fun downloadImages(groups: List<RankedProductGroup>) {
        for (group in groups) {
            for (product in group.products) {
                downloadProductImage(product)
            }
        }
    }

    fun downloadProductImage(product: RPGProduct) {
        val url = IMAGE_BASE_URL + product.imageUrl
        cachingFetcher.fetchFile(url, reason = product.name)
    }
}