package rubery.totoro.digital.ui.composable.screen.shared.loadingplaceholder

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize

private const val ShimmerDurationMillis = 2000

/** Horizontal shimmer sweep, left to right. */
@Composable
fun LoadingPlaceholder(modifier: Modifier = Modifier) {
    var size by remember { mutableStateOf(IntSize.Zero) }

    val transition = rememberInfiniteTransition(label = "workout_image_shimmer_horizontal")
    val sweep by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = ShimmerDurationMillis, easing = LinearEasing),
        ),
        label = "workout_image_shimmer_horizontal_sweep",
    )

    val baseColor = MaterialTheme.colorScheme.surfaceContainerHighest
    val highlightColor = lerp(baseColor, MaterialTheme.colorScheme.surfaceContainerLowest, 0.5f)

    val width = size.width.toFloat()
    val leftEdge = width * (2f * sweep - 1f)

    Box(
        modifier = modifier
            .onSizeChanged { size = it }
            .background(
                Brush.linearGradient(
                    colorStops = arrayOf(
                        0.0f to baseColor,
                        0.35f to baseColor,
                        0.5f to highlightColor,
                        0.65f to baseColor,
                        1.0f to baseColor,
                    ),
                    start = Offset(x = leftEdge, y = 0f),
                    end = Offset(x = leftEdge + width, y = 0f),
                ),
            ),
    )
}
