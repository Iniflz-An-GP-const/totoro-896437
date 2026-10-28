package rubery.totoro.digital.ui.composable.screen.shared

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private val AccentEdgeWidth = 4.dp

@Composable
fun typeAccentColor(typeOrdinal: Int): Color {
    val accents = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.tertiary,
        MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.inversePrimary,
    )
    return accents[typeOrdinal % accents.size]
}

fun Modifier.accentEdge(color: Color, width: Dp = AccentEdgeWidth): Modifier = drawWithContent {
    drawContent()
    drawRect(color = color, topLeft = Offset.Zero, size = Size(width.toPx(), size.height))
}
