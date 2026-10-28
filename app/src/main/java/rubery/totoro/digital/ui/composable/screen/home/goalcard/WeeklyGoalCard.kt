package rubery.totoro.digital.ui.composable.screen.home.goalcard

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import rubery.totoro.digital.R
import rubery.totoro.digital.ui.theme.LocalDimens
import rubery.totoro.digital.ui.theme.cardBorderStroke

private val ProgressBarHeight = 8.dp

private const val ProgressFillMs = 800

@Composable
fun WeeklyGoalCard(
    weeklyGoal: Int,
    completed: Int,
    modifier: Modifier = Modifier,
) {
    val dimens = LocalDimens.current
    val progress = if (weeklyGoal > 0) (completed.toFloat() / weeklyGoal).coerceIn(0f, 1f) else 0f
    val shownProgress = remember { Animatable(0f) }

    LaunchedEffect(progress) {
        shownProgress.animateTo(progress, tween(durationMillis = ProgressFillMs))
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(dimens.cardCornerRadius),
        border = cardBorderStroke(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
    ) {
        Column(
            modifier = Modifier
                .padding(dimens.cardPadding)
                .fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.home_weekly_goal_label),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                Text(
                    text = stringResource(
                        R.string.home_weekly_goal_progress_format,
                        completed,
                        weeklyGoal
                    ),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            Spacer(modifier = Modifier.height(dimens.itemSpacing))

            LinearProgressIndicator(
                progress = { shownProgress.value },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(ProgressBarHeight)
                    .clip(RoundedCornerShape(dimens.controlCornerRadius)),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceContainerHighest,
            )
        }
    }
}
