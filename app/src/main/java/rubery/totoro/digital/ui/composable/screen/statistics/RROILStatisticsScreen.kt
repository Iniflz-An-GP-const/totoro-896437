package rubery.totoro.digital.ui.composable.screen.statistics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconToggleButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import rubery.totoro.digital.R
import rubery.totoro.digital.data.entity.WorkoutSessionEntity
import rubery.totoro.digital.data.model.Difficulty
import rubery.totoro.digital.data.model.WorkoutType
import rubery.totoro.digital.ui.composable.approot.LocalContentBottomPadding
import rubery.totoro.digital.ui.composable.screen.shared.DataEmptyContent
import rubery.totoro.digital.ui.composable.screen.statistics.chart.BarChart
import rubery.totoro.digital.ui.composable.screen.statistics.summary.design.StatsSummary
import rubery.totoro.digital.ui.state.DataUiState
import rubery.totoro.digital.ui.state.WorkoutFilterMode
import rubery.totoro.digital.ui.theme.LocalDimens
import rubery.totoro.digital.ui.viewmodel.StatisticsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun RROILStatisticsScreen(
    modifier: Modifier = Modifier,
    viewModel: StatisticsViewModel = koinViewModel(),
) {
    val sessionsState by viewModel.sessionsState.collectAsStateWithLifecycle()
    val chartMode by viewModel.chartMode.collectAsStateWithLifecycle()

    val sessions =
        (sessionsState as? DataUiState.Populated<List<WorkoutSessionEntity>>)?.data.orEmpty()

    StatisticsContent(
        sessions = sessions,
        chartMode = chartMode,
        onChartModeToggled = viewModel::onChartModeToggled,
        modifier = modifier,
    )
}

@Composable
private fun StatisticsContent(
    sessions: List<WorkoutSessionEntity>,
    chartMode: WorkoutFilterMode,
    onChartModeToggled: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val dimens = LocalDimens.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                start = dimens.screenPadding,
                end = dimens.screenPadding,
                top = dimens.screenPadding,
                bottom = dimens.screenPadding + LocalContentBottomPadding.current,
            ),
    ) {
        if (sessions.isEmpty()) {
            DataEmptyContent(
                primaryText = stringResource(R.string.statistics_state_empty),
                modifier = Modifier.fillMaxSize(),
            )
        } else {
            val isTypeMode = chartMode == WorkoutFilterMode.TYPE

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(
                        if (isTypeMode) {
                            R.string.statistics_chart_title_type
                        } else {
                            R.string.statistics_chart_title_difficulty
                        }
                    ),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                OutlinedIconToggleButton(
                    checked = !isTypeMode,
                    onCheckedChange = { onChartModeToggled() },
                    shape = RoundedCornerShape(dimens.controlCornerRadius),
                    colors = IconButtonDefaults.outlinedIconToggleButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        checkedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        checkedContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    ),
                ) {
                    Icon(
                        imageVector = Icons.Filled.SwapHoriz,
                        contentDescription = stringResource(
                            if (isTypeMode) {
                                R.string.workout_filter_mode_difficulty
                            } else {
                                R.string.workout_filter_mode_type
                            }
                        ),
                    )
                }
            }

            Spacer(modifier = Modifier.height(dimens.sectionGap))

            val counts = remember(sessions, chartMode) {
                when (chartMode) {
                    WorkoutFilterMode.TYPE ->
                        WorkoutType.entries.map { type -> sessions.count { it.workoutType == type.name } }

                    WorkoutFilterMode.DIFFICULTY ->
                        Difficulty.entries.map { difficulty -> sessions.count { it.difficulty == difficulty.name } }
                }
            }
            val labels = when (chartMode) {
                WorkoutFilterMode.TYPE -> WorkoutType.entries.map { stringResource(it.titleRes) }
                WorkoutFilterMode.DIFFICULTY -> Difficulty.entries.map { stringResource(it.titleRes) }
            }

            BarChart(
                entries = labels.zip(counts),
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(dimens.sectionGap))

            StatsSummary(
                sessions = sessions,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
