package rubery.totoro.digital.ui.composable.screen.shared.listmotion

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import rubery.totoro.digital.ui.composable.screen.shared.listmotion.timing.ListMotionTiming
import rubery.totoro.digital.ui.composable.screen.shared.listmotion.listEntranceEffect

val LocalPlayedEntrances = staticCompositionLocalOf<MutableSet<Any>?> { null }

@Composable
fun Modifier.listEntrance(key: Any, index: Int): Modifier {
    if (LocalInspectionMode.current) return this

    val played = LocalPlayedEntrances.current
    val alreadyPlayed = remember(key) { played?.contains(key) == true }
    val progress = remember(key) { Animatable(if (alreadyPlayed) 1f else 0f) }

    LaunchedEffect(key) {
        if (!alreadyPlayed) {
            progress.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = ListMotionTiming.durationMs,
                    delayMillis = index.coerceIn(
                        0,
                        ListMotionTiming.staggeredItems
                    ) * ListMotionTiming.staggerMs,
                    easing = FastOutSlowInEasing,
                ),
            )
            played?.add(key)
        }
    }

    return listEntranceEffect { progress.value }
}
