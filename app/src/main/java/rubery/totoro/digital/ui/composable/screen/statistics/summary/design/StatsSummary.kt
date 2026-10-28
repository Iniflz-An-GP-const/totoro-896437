package rubery.totoro.digital.ui.composable.screen.statistics.summary.design

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import rubery.totoro.digital.R
import rubery.totoro.digital.data.entity.WorkoutSessionEntity
import rubery.totoro.digital.ui.composable.screen.statistics.summary.StatItemData
import rubery.totoro.digital.ui.composable.screen.statistics.summary.content.StatItemContent
import rubery.totoro.digital.ui.composable.screen.statistics.summary.shell.StatsSummaryShell
import java.time.LocalDate

@Composable
fun StatsSummary(
    sessions: List<WorkoutSessionEntity>,
    modifier: Modifier = Modifier,
) {
    val totalWorkouts = sessions.size
    val completionRate = remember(sessions) {
        if (sessions.isEmpty()) 0 else (sessions.count { it.isFullyCompleted } * 100) / sessions.size
    }
    val currentStreak = remember(sessions) {
        val sessionDates = sessions.map { it.timestamp.toLocalDate() }.toSet()
        var streak = 0
        var day = LocalDate.now()
        while (sessionDates.contains(day)) {
            streak++
            day = day.minusDays(1)
        }
        streak
    }

    val items = listOf(
        StatItemData(
            value = totalWorkouts.toString(),
            label = stringResource(R.string.statistics_summary_total),
            icon = Icons.Filled.FitnessCenter,
            iconContainerColor = MaterialTheme.colorScheme.primaryContainer,
            iconColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        StatItemData(
            value = stringResource(R.string.statistics_summary_percent_format, completionRate),
            label = stringResource(R.string.statistics_summary_completion),
            icon = Icons.Filled.CheckCircle,
            iconContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            iconColor = MaterialTheme.colorScheme.onSecondaryContainer,
        ),
        StatItemData(
            value = stringResource(R.string.statistics_summary_days_format, currentStreak),
            label = stringResource(R.string.statistics_summary_streak),
            icon = Icons.Filled.LocalFireDepartment,
            iconContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            iconColor = MaterialTheme.colorScheme.onTertiaryContainer,
        ),
    )

    StatsSummaryShell(items = items, modifier = modifier) { item ->
        StatItemContent(item = item)
    }
}
