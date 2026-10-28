package rubery.totoro.digital.ui.composable.screen.shared.pulse

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import rubery.totoro.digital.ui.composable.screen.shared.pulse.PulseTiming
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

private const val BUMP_SCALE_DELTA = 0.07f
private const val BUMPS = 2

private const val BURST_SHARE = 0.4f
private const val BUMP_UP_SHARE = 0.43f

@Composable
@Suppress("UNUSED_PARAMETER")
fun Modifier.pulseEffect(cornerRadius: Dp, timing: PulseTiming): Modifier {
    if (timing.isOff) return this

    val scale = remember { Animatable(1f) }
    val peak = 1f + BUMP_SCALE_DELTA * timing.intensity
    val bumpMs = timing.durationMs * BURST_SHARE / BUMPS
    val upMs = (bumpMs * BUMP_UP_SHARE).toInt()
    val downMs = (bumpMs - upMs).toInt()

    LaunchedEffect(timing) {
        while (true) {
            delay(timing.pauseMs.toLong().milliseconds)
            repeat(BUMPS) {
                scale.animateTo(peak, tween(durationMillis = upMs))
                scale.animateTo(1f, tween(durationMillis = downMs))
            }
        }
    }

    return graphicsLayer {
        scaleX = scale.value
        scaleY = scale.value
    }
}
