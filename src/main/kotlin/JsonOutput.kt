package eden.drivethru

import eden.drivethru.models.ViewModel
import kotlinx.serialization.json.Json
import java.nio.file.Path
import kotlin.io.path.writeText

class JsonOutput(private val outputPath: Path) : ProductOutput {
    private val encoder = Json { prettyPrint = true }

    override fun write(data: ViewModel) {
        val json = encoder.encodeToString(data)
        outputPath.writeText("const data = $json")
    }
}