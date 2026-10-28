package rubery.totoro.digital.ui.composable.screen.onboarding.textmotion

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

private val RISE_DISTANCE = 14.dp

fun Modifier.textMotionEffect(progress: () -> Float): Modifier = graphicsLayer {
    val p = progress()
    alpha = p
    translationY = (1f - p) * RISE_DISTANCE.toPx()
}
