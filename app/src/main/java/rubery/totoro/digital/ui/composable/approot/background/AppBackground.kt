package rubery.totoro.digital.ui.composable.approot.background

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.sp
import rubery.totoro.digital.R
import kotlin.math.ceil

private const val TiltDegrees = -20f
private const val TextAlpha = 0.035f

// The gaps between the names, as a share of the name's own size.
private const val ColumnGapShare = 0.7f
private const val RowGapShare = 2.2f

/**
 * The name of the app, repeated across the whole screen in big bold letters, in rows tilted upward, each row shifted
 * by half a name against the one above, like a stamp on a document. Very faint and static.
 */
@Composable
fun AppBackground(modifier: Modifier = Modifier) {
    val name = stringResource(R.string.app_name)
    val color = MaterialTheme.colorScheme.onBackground.copy(alpha = TextAlpha)
    val style = MaterialTheme.typography.displaySmall.copy(
        fontWeight = FontWeight.Bold,
        letterSpacing = 2.sp,
    )
    val measurer = rememberTextMeasurer()
    val label = remember(name, style) { measurer.measure(text = name, style = style) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .drawWithCache {
                val cellWidth = label.size.width * (1f + ColumnGapShare)
                val cellHeight = label.size.height * (1f + RowGapShare)

                // The rows are tilted, so draw over an area wider than the screen to leave no corner empty.
                val columns = ceil(size.width * 2f / cellWidth).toInt() + 2
                val rows = ceil(size.height * 2f / cellHeight).toInt() + 2
                val origin = Offset(-size.width / 2f, -size.height / 2f)

                onDrawBehind {
                    rotate(degrees = TiltDegrees, pivot = center) {
                        repeat(rows) { row ->
                            val shift = if (row % 2 == 0) 0f else cellWidth / 2f

                            repeat(columns) { column ->
                                drawText(
                                    textLayoutResult = label,
                                    color = color,
                                    topLeft = Offset(
                                        x = origin.x + column * cellWidth - shift,
                                        y = origin.y + row * cellHeight,
                                    ),
                                )
                            }
                        }
                    }
                }
            },
    )
}
