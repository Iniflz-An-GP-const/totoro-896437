package rubery.totoro.digital.ui.composable.screen.workout.completed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import rubery.totoro.digital.R
import rubery.totoro.digital.data.model.Difficulty
import rubery.totoro.digital.ui.theme.LocalDimens

private val BarWidth = 6.dp
private val BarHeight = 16.dp
private const val UnfilledAlpha = 0.25f

@Composable
fun SummaryStats(
    summary: WorkoutSummary,
    modifier: Modifier = Modifier,
) {
    val dimens = LocalDimens.current

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimens.tightSpacing),
    ) {
        StatTile(
            label = stringResource(R.string.workout_completed_stat_exercises),
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = summary.exerciseCount.toString(),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        StatTile(
            label = stringResource(R.string.workout_completed_stat_sets),
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = summary.setCount.toString(),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        StatTile(
            label = stringResource(R.string.workout_completed_stat_level),
            modifier = Modifier.weight(1f),
        ) {
            LevelBars(level = summary.difficultyLevel)
        }
    }
}

@Composable
private fun StatTile(
    label: String,
    modifier: Modifier = Modifier,
    value: @Composable () -> Unit,
) {
    val dimens = LocalDimens.current

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(dimens.controlCornerRadius),
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimens.itemSpacing, horizontal = dimens.tightSpacing),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier.height(BarHeight + 8.dp),
                contentAlignment = Alignment.Center,
            ) {
                value()
            }

            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun LevelBars(level: Int) {
    val color = MaterialTheme.colorScheme.primary

    Row(horizontalArrangement = Arrangement.spacedBy(3.dp), verticalAlignment = Alignment.Bottom) {
        repeat(Difficulty.entries.size) { index ->
            Box(
                modifier = Modifier
                    .size(
                        width = BarWidth,
                        height = BarHeight * (index + 1) / Difficulty.entries.size
                    )
                    .background(
                        color = if (index < level) color else color.copy(alpha = UnfilledAlpha),
                        shape = RoundedCornerShape(BarWidth / 2),
                    ),
            )
        }
    }
}
