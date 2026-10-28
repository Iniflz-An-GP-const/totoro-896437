package rubery.totoro.digital.ui.composable.screen.workout.completed

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import rubery.totoro.digital.R
import rubery.totoro.digital.ui.composable.screen.workout.completed.SummaryStats
import rubery.totoro.digital.ui.composable.screen.workout.completed.WorkoutSummary
import rubery.totoro.digital.ui.theme.LocalDimens

private val MedalSize = 104.dp
private val MedalRingWidth = 4.dp
private val TrophySize = 52.dp
private const val MedalStartScale = 0.6f

@Composable
fun WorkoutCompletedDialog(
    summary: WorkoutSummary,
    onDismiss: () -> Unit,
) {
    val dimens = LocalDimens.current
    val medalScale = remember { Animatable(MedalStartScale) }

    LaunchedEffect(Unit) {
        medalScale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioNoBouncy,
                stiffness = Spring.StiffnessLow
            ),
        )
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(dimens.cardCornerRadius),
            color = MaterialTheme.colorScheme.surface,
        ) {
            Column(
                modifier = Modifier.padding(dimens.cardPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .graphicsLayer {
                            scaleX = medalScale.value
                            scaleY = medalScale.value
                            alpha = (medalScale.value - MedalStartScale) / (1f - MedalStartScale)
                        }
                        .size(MedalSize)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.tertiary
                                ),
                            ),
                        )
                        .border(MedalRingWidth, MaterialTheme.colorScheme.surface, CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Filled.EmojiEvents,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(TrophySize),
                    )
                }

                Spacer(modifier = Modifier.height(dimens.sectionGap))

                Text(
                    text = stringResource(R.string.workout_finished_title),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(dimens.tightSpacing))

                Text(
                    text = summary.workoutName,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(dimens.tightSpacing))

                Text(
                    text = stringResource(R.string.workout_finished_description),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(dimens.sectionGap))

                SummaryStats(summary = summary)

                Spacer(modifier = Modifier.height(dimens.sectionGap))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(dimens.controlCornerRadius),
                ) {
                    Text(stringResource(R.string.workout_completed_dialog_confirm))
                }
            }
        }
    }
}
