package rubery.totoro.digital.ui.composable.screen.onboarding.motion

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.StartOffsetType
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import rubery.totoro.digital.ui.composable.screen.onboarding.motion.MotionTiming
import kotlin.math.PI
import kotlin.math.sin

private const val SWAY_DEGREES = 8f

@Composable
fun Modifier.iconMotionEffect(index: Int, timing: MotionTiming): Modifier {
    if (timing.isOff) return this

    val progress = rememberInfiniteTransition(label = "iconv_sway").animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = timing.durationMs * 2, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
            initialStartOffset = StartOffset(index * timing.staggerMs, StartOffsetType.Delay),
        ),
        label = "iconv_swayProgress",
    )

    return graphicsLayer {
        rotationZ = sin(2f * PI.toFloat() * progress.value) * SWAY_DEGREES * timing.intensity
    }
}
