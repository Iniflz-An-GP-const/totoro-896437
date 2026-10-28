package rubery.totoro.digital.ui.composable.screen.home.tips.shell

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import rubery.totoro.digital.data.model.TipModel
import rubery.totoro.digital.ui.composable.screen.home.tips.transition.TipTransition
import rubery.totoro.digital.ui.theme.LocalDimens

private val WatermarkSize = 104.dp
private val WatermarkOffset = 22.dp
private const val WatermarkAlpha = 0.14f
private const val WatermarkTilt = -20f

@Composable
fun TipCardShell(
    tip: TipModel,
    onAdvance: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (TipModel) -> Unit,
) {
    val dimens = LocalDimens.current
    val contentColor = MaterialTheme.colorScheme.onPrimaryContainer

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(dimens.cardCornerRadius))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clickable(onClick = onAdvance),
    ) {
        Icon(
            imageVector = Icons.Filled.FitnessCenter,
            contentDescription = null,
            tint = contentColor.copy(alpha = WatermarkAlpha),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = WatermarkOffset, y = WatermarkOffset)
                .size(WatermarkSize)
                .rotate(WatermarkTilt),
        )

        CompositionLocalProvider(LocalContentColor provides contentColor) {
            TipTransition(
                tip = tip,
                modifier = Modifier.padding(dimens.cardPadding),
                content = content,
            )
        }
    }
}
