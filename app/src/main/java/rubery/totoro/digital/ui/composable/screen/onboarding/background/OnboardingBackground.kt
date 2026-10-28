package rubery.totoro.digital.ui.composable.screen.onboarding.background

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke

private const val RingAlpha = 0.28f
private const val InnerRingAlpha = 0.16f

private const val ThickShare = 0.22f
private const val ThinShare = 0.07f
private const val InnerRadiusShare = 0.72f

// How far the rings slide per page, as a share of the screen width.
private const val TopShift = 0.08f
private const val BottomShift = 0.14f

@Composable
fun OnboardingBackground(
    pageFraction: () -> Float,
    modifier: Modifier = Modifier,
) {
    val top = MaterialTheme.colorScheme.primary
    val bottom = MaterialTheme.colorScheme.tertiary

    Canvas(modifier = modifier.fillMaxSize()) {
        val page = pageFraction()

        drawOrb(
            color = top,
            center = Offset(size.width * (0.05f - TopShift * page), size.height * 0.12f),
            radius = size.width * 0.55f,
            fadeTowards = Offset(size.width, size.height),
        )
        drawOrb(
            color = bottom,
            center = Offset(size.width * (1.0f - BottomShift * page), size.height * 0.86f),
            radius = size.width * 0.5f,
            fadeTowards = Offset(0f, 0f),
        )
    }
}

private fun DrawScope.drawOrb(
    color: Color,
    center: Offset,
    radius: Float,
    fadeTowards: Offset,
) {
    val brush = Brush.linearGradient(
        colors = listOf(color.copy(alpha = RingAlpha), Color.Transparent),
        start = center,
        end = fadeTowards,
    )
    val innerBrush = Brush.linearGradient(
        colors = listOf(color.copy(alpha = InnerRingAlpha), Color.Transparent),
        start = center,
        end = fadeTowards,
    )

    drawCircle(
        brush = brush,
        radius = radius,
        center = center,
        style = Stroke(width = radius * ThickShare),
    )
    drawCircle(
        brush = innerBrush,
        radius = radius * InnerRadiusShare,
        center = center,
        style = Stroke(width = radius * ThinShare),
    )
}
