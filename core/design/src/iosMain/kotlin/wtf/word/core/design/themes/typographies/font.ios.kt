package wtf.word.core.design.themes.typographies

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource

private val cache: MutableMap<String, Font> = mutableMapOf()

//@OptIn(ExperimentalResourceApi::class)
//@Composable
//actual fun font(name: String, res: String, weight: FontWeight, style: FontStyle): Font {
//    return cache.getOrPut(res) {
//        org.jetbrains.compose.resources.Font(FontResource("font/$res.ttf"), weight, style)
//    }
//}
@OptIn(ExperimentalResourceApi::class)
@Composable
actual fun font(name: String, res: String, weight: FontWeight, style: FontStyle): Font {
    return cache.getOrPut(res) {
        val byteArray = runBlocking {
            try {
                resource("font/$res.ttf").readBytes()
            } catch (e: Exception) {
                resource("font/$res.otf").readBytes()
            }
        }

        androidx.compose.ui.text.platform.Font(res, byteArray, weight, style)
    }
}