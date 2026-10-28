package rubery.totoro.digital.ui.composable.screen.onboarding.textmotion

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import rubery.totoro.digital.ui.composable.screen.onboarding.motion.LocalOnboardingPageCurrent
import rubery.totoro.digital.ui.composable.screen.onboarding.textmotion.timing.TextMotionTiming
import rubery.totoro.digital.ui.composable.screen.onboarding.textmotion.textMotionEffect

@Composable
fun Modifier.textMotion(index: Int): Modifier {
    if (LocalInspectionMode.current) return this

    val current = LocalOnboardingPageCurrent.current
    val progress = remember { Animatable(0f) }

    LaunchedEffect(current) {
        if (current) {
            progress.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = TextMotionTiming.durationMs,
                    delayMillis = index * TextMotionTiming.staggerMs,
                    easing = FastOutSlowInEasing,
                ),
            )
        }
    }

    return textMotionEffect { progress.value }
}
