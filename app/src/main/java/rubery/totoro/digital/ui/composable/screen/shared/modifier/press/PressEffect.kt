package rubery.totoro.digital.ui.composable.screen.shared.modifier.press

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

private const val PRESSED_DEPTH = 0.06f
private const val POP_GAIN = 8f
private const val MAX_POP = 0.04f

fun Modifier.pressEffect(progress: () -> Float, strength: Float): Modifier = graphicsLayer {
    val value = progress()
    val kick = if (value < 0f) value * POP_GAIN else value
    val scale = (1f - kick * PRESSED_DEPTH * strength).coerceAtMost(1f + MAX_POP * strength)
    scaleX = scale
    scaleY = scale
}
