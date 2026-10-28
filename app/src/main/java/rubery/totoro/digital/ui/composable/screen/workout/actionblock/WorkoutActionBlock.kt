package rubery.totoro.digital.ui.composable.screen.workout.actionblock

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import rubery.totoro.digital.R
import rubery.totoro.digital.ui.composable.approot.LocalContentBottomPadding
import rubery.totoro.digital.ui.composable.screen.shared.modifier.press.PressStrength
import rubery.totoro.digital.ui.composable.screen.shared.modifier.press.pressFeedback
import rubery.totoro.digital.ui.composable.screen.shared.pulse.actionPulse
import rubery.totoro.digital.ui.theme.LocalDimens

private val ActionButtonHeight = 52.dp

@Composable
fun WorkoutActionBlock(
    isInProgress: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val dimens = LocalDimens.current
    val buttonShape = RoundedCornerShape(dimens.controlCornerRadius)
    val gradient = if (isInProgress) {
        Brush.horizontalGradient(
            listOf(
                MaterialTheme.colorScheme.error,
                MaterialTheme.colorScheme.errorContainer,
            ),
        )
    } else {
        Brush.horizontalGradient(
            listOf(
                MaterialTheme.colorScheme.primary,
                MaterialTheme.colorScheme.tertiary,
            ),
        )
    }
    val contentColor = if (isInProgress) {
        MaterialTheme.colorScheme.onError
    } else {
        MaterialTheme.colorScheme.onPrimary
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainerHighest)
            .padding(
                start = dimens.cardPadding,
                end = dimens.cardPadding,
                top = dimens.cardPadding,
                bottom = dimens.cardPadding + LocalContentBottomPadding.current,
            ),
    ) {
        val interactionSource = remember { MutableInteractionSource() }

        Box(
            modifier = Modifier
                .actionPulse(dimens.controlCornerRadius, enabled = !isInProgress)
                .pressFeedback(interactionSource, PressStrength.Button)
                .fillMaxWidth()
                .height(ActionButtonHeight)
                .clip(buttonShape)
                .background(gradient)
                .clickable(
                    interactionSource = interactionSource,
                    indication = LocalIndication.current,
                    onClick = onClick,
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(
                    if (isInProgress) R.string.end_workout_button_title else R.string.start_workout_button_title
                ),
                color = contentColor,
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}
