package rubery.totoro.digital.ui.composable.screen.home.filter

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

private const val POP_SCALE = 1.06f
private const val POP_UP_MS = 90

@Composable
fun Modifier.selectionPop(selected: Boolean): Modifier {
    val scale = remember { Animatable(1f) }
    var isFirstComposition by remember { mutableStateOf(true) }

    LaunchedEffect(selected) {
        if (isFirstComposition) {
            isFirstComposition = false
        } else if (selected) {
            scale.animateTo(POP_SCALE, tween(POP_UP_MS))
            scale.animateTo(
                1f,
                spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
        }
    }

    return graphicsLayer {
        scaleX = scale.value
        scaleY = scale.value
    }
}
