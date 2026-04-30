package eden.drivethru.output

import eden.drivethru.models.ViewModel
import java.nio.file.Path
import kotlin.io.path.writer

class PlainTextOutputTarget(val outputPath: Path) : OutputTarget {
    override fun write(data: ViewModel) {
        outputPath.writer().use { f ->
            f.write(data.lastUpdated + "\n\n")
            for (group in data.groups) {
                f.write("# ${group.rank}\n")
                for (product in group.products) {
                    f.write("- ${product.name}\n")
                }
                f.write("\n")
            }
        }
    }
}