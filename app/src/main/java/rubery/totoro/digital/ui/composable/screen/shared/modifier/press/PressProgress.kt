package rubery.totoro.digital.ui.composable.screen.shared.modifier.press

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun rememberPressProgress(
    interactionSource: InteractionSource,
    tempo: PressTempo,
): Animatable<Float, AnimationVector1D> {
    val progress = remember { Animatable(0f) }

    LaunchedEffect(interactionSource, tempo) {
        interactionSource.interactions.collectLatest { interaction ->
            when (interaction) {
                is PressInteraction.Press -> progress.animateTo(1f, tween(tempo.pressMs))
                is PressInteraction.Release, is PressInteraction.Cancel -> {
                    progress.animateTo(1f, tween(tempo.pressMs))
                    progress.animateTo(
                        targetValue = 0f,
                        animationSpec = spring(
                            dampingRatio = tempo.dampingRatio,
                            stiffness = tempo.stiffness,
                        ),
                    )
                }
            }
        }
    }

    return progress
}
