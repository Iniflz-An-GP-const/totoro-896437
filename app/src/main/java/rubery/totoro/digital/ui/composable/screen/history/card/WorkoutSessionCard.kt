package rubery.totoro.digital.ui.composable.screen.history.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import rubery.totoro.digital.R
import rubery.totoro.digital.data.entity.WorkoutSessionEntity
import rubery.totoro.digital.data.model.Difficulty
import rubery.totoro.digital.data.model.WorkoutType
import rubery.totoro.digital.ui.theme.LocalDimens
import rubery.totoro.digital.ui.theme.cardBorderStroke

private val IconBadgeSize = 40.dp
private val IconSize = 20.dp
private val LevelBarWidth = 4.dp
private val LevelBarHeight = 12.dp
private val LevelBarSpacing = 2.dp
private const val LevelBarUnfilledAlpha = 0.3f

@Composable
fun WorkoutSessionCard(
    session: WorkoutSessionEntity,
    modifier: Modifier = Modifier,
) {
    val dimens = LocalDimens.current
    val type = remember(session.workoutType) { WorkoutType.valueOf(session.workoutType) }
    val difficulty = remember(session.difficulty) { Difficulty.valueOf(session.difficulty) }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(dimens.cardCornerRadius),
        border = cardBorderStroke(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
    ) {
        Row(
            modifier = Modifier
                .padding(dimens.cardPadding)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(IconBadgeSize)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = type.icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(IconSize),
                )
            }

            Spacer(modifier = Modifier.width(dimens.itemSpacing))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = session.workoutName,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(modifier = Modifier.height(dimens.tightSpacing))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(type.titleRes),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )

                    Spacer(modifier = Modifier.width(dimens.itemSpacing))

                    DifficultyLevelBars(
                        level = difficulty.level,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            Spacer(modifier = Modifier.width(dimens.itemSpacing))

            StatusChip(
                isCompleted = session.isFullyCompleted,
                completed = session.completedExercises,
                total = session.totalExercises,
            )
        }
    }
}

@Composable
private fun DifficultyLevelBars(
    level: Int,
    color: Color,
    modifier: Modifier = Modifier,
    maxLevel: Int = Difficulty.entries.size,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(LevelBarSpacing),
    ) {
        repeat(maxLevel) { index ->
            Box(
                modifier = Modifier
                    .size(width = LevelBarWidth, height = LevelBarHeight)
                    .background(
                        color = if (index < level) color else color.copy(alpha = LevelBarUnfilledAlpha),
                        shape = RoundedCornerShape(LevelBarWidth / 2),
                    ),
            )
        }
    }
}

@Composable
private fun StatusChip(
    isCompleted: Boolean,
    completed: Int,
    total: Int,
    modifier: Modifier = Modifier,
) {
    val dimens = LocalDimens.current
    val containerColor = if (isCompleted) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.surfaceContainerHighest
    }
    val contentColor = if (isCompleted) {
        MaterialTheme.colorScheme.onPrimaryContainer
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(dimens.controlCornerRadius))
            .background(containerColor)
            .padding(horizontal = dimens.itemSpacing, vertical = dimens.tightSpacing),
    ) {
        Text(
            text = if (isCompleted) {
                stringResource(R.string.history_session_completed_label)
            } else {
                stringResource(R.string.history_session_progress, completed, total)
            },
            style = MaterialTheme.typography.labelSmall,
            color = contentColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
