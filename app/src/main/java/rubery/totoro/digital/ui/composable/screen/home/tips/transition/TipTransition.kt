package rubery.totoro.digital.ui.composable.screen.home.tips.transition

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import rubery.totoro.digital.data.model.TipModel

@Composable
fun TipTransition(
    tip: TipModel,
    modifier: Modifier = Modifier,
    content: @Composable (TipModel) -> Unit,
) {
    AnimatedContent(
        targetState = tip,
        modifier = modifier,
        transitionSpec = {
            (slideInVertically(initialOffsetY = { height -> height / 3 }) + fadeIn())
                .togetherWith(slideOutVertically(targetOffsetY = { height -> -height / 3 }) + fadeOut())
        },
        label = "tip",
    ) { animatedTip ->
        content(animatedTip)
    }
}
