package rubery.totoro.digital.ui.composable.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import rubery.totoro.digital.R
import rubery.totoro.digital.data.model.Difficulty
import rubery.totoro.digital.data.model.TipModel
import rubery.totoro.digital.data.model.WorkoutModel
import rubery.totoro.digital.data.model.WorkoutType
import rubery.totoro.digital.ui.composable.approot.LocalContentBottomPadding
import rubery.totoro.digital.ui.composable.screen.home.filter.WorkoutFilterRow
import rubery.totoro.digital.ui.composable.screen.home.layout.homeTopSection
import rubery.totoro.digital.ui.composable.screen.home.workoutcard.WorkoutCard
import rubery.totoro.digital.ui.composable.screen.shared.DataBasedContainer
import rubery.totoro.digital.ui.composable.screen.shared.DataEmptyContent
import rubery.totoro.digital.ui.composable.screen.shared.listmotion.LocalPlayedEntrances
import rubery.totoro.digital.ui.composable.screen.shared.listmotion.listEntrance
import rubery.totoro.digital.ui.state.DataUiState
import rubery.totoro.digital.ui.state.WorkoutFilterMode
import rubery.totoro.digital.ui.theme.ControlDivider
import rubery.totoro.digital.ui.theme.LocalDimens
import rubery.totoro.digital.ui.viewmodel.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun RROILHomeScreen(
    onWorkoutClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val workoutsState by viewModel.workoutsState.collectAsStateWithLifecycle()
    val filterMode by viewModel.filterMode.collectAsStateWithLifecycle()
    val selectedType by viewModel.selectedType.collectAsStateWithLifecycle()
    val selectedDifficulty by viewModel.selectedDifficulty.collectAsStateWithLifecycle()
    val weeklyGoal by viewModel.weeklyGoal.collectAsStateWithLifecycle()
    val weeklyProgress by viewModel.weeklyProgress.collectAsStateWithLifecycle()
    val tips by viewModel.tips.collectAsStateWithLifecycle()

    HomeContent(
        workoutsState = workoutsState,
        filterMode = filterMode,
        selectedType = selectedType,
        selectedDifficulty = selectedDifficulty,
        weeklyGoal = weeklyGoal,
        weeklyProgress = weeklyProgress,
        tips = tips,
        onFilterModeToggled = viewModel::onFilterModeToggled,
        onTypeSelected = viewModel::onTypeSelected,
        onDifficultySelected = viewModel::onDifficultySelected,
        onWorkoutClick = onWorkoutClick,
        modifier = modifier,
    )
}

@Composable
private fun HomeContent(
    workoutsState: DataUiState<List<WorkoutModel>>,
    filterMode: WorkoutFilterMode,
    selectedType: WorkoutType?,
    selectedDifficulty: Difficulty?,
    weeklyGoal: Int,
    weeklyProgress: Int,
    tips: List<TipModel>,
    onFilterModeToggled: () -> Unit,
    onTypeSelected: (WorkoutType?) -> Unit,
    onDifficultySelected: (Difficulty?) -> Unit,
    onWorkoutClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    DataBasedContainer(
        modifier = modifier.fillMaxSize(),
        dataState = workoutsState,

        dataPopulated = {
            val workouts = (workoutsState as DataUiState.Populated<List<WorkoutModel>>).data

            WorkoutsList(
                workouts = workouts,
                filterMode = filterMode,
                selectedType = selectedType,
                selectedDifficulty = selectedDifficulty,
                weeklyGoal = weeklyGoal,
                weeklyProgress = weeklyProgress,
                tips = tips,
                onFilterModeToggled = onFilterModeToggled,
                onTypeSelected = onTypeSelected,
                onDifficultySelected = onDifficultySelected,
                onWorkoutClick = onWorkoutClick,
            )
        },

        dataEmpty = {
            DataEmptyContent(
                primaryText = stringResource(R.string.workouts_state_empty),
                modifier = Modifier.fillMaxSize(),
            )
        },
    )
}

@Composable
private fun WorkoutsList(
    workouts: List<WorkoutModel>,
    filterMode: WorkoutFilterMode,
    selectedType: WorkoutType?,
    selectedDifficulty: Difficulty?,
    weeklyGoal: Int,
    weeklyProgress: Int,
    tips: List<TipModel>,
    onFilterModeToggled: () -> Unit,
    onTypeSelected: (WorkoutType?) -> Unit,
    onDifficultySelected: (Difficulty?) -> Unit,
    onWorkoutClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val dimens = LocalDimens.current

    val playedEntrances = remember { mutableSetOf<Any>() }

    val filteredWorkouts = remember(workouts, selectedType, selectedDifficulty) {
        workouts.filter { workout ->
            (selectedType == null || workout.type == selectedType) &&
                    (selectedDifficulty == null || workout.difficulty == selectedDifficulty)
        }
    }

    CompositionLocalProvider(LocalPlayedEntrances provides playedEntrances) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = dimens.screenPadding,
                bottom = dimens.screenPadding + LocalContentBottomPadding.current,
            ),
            verticalArrangement = Arrangement.spacedBy(dimens.contentSpacing),
        ) {
            homeTopSection(
                tips = tips,
                weeklyGoal = weeklyGoal,
                weeklyProgress = weeklyProgress,
            )

            stickyHeader {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceContainer),
                ) {
                    WorkoutFilterRow(
                        modifier = Modifier.padding(vertical = dimens.tightSpacing),
                        filterMode = filterMode,
                        selectedType = selectedType,
                        selectedDifficulty = selectedDifficulty,
                        onFilterModeToggled = onFilterModeToggled,
                        onTypeSelected = onTypeSelected,
                        onDifficultySelected = onDifficultySelected,
                    )

                    ControlDivider()
                }
            }

            if (filteredWorkouts.isEmpty()) {
                item {
                    DataEmptyContent(
                        primaryText = stringResource(R.string.workout_filter_empty),
                        modifier = Modifier.fillParentMaxSize(),
                    )
                }
            } else {
                itemsIndexed(
                    filteredWorkouts,
                    key = { _, workout -> workout.id }) { index, workout ->
                    WorkoutCard(
                        workout = workout,
                        modifier = Modifier
                            .listEntrance(key = workout.id, index = index)
                            .padding(horizontal = dimens.screenPadding),
                        onClick = { onWorkoutClick(workout.id) },
                    )
                }
            }
        }
    }
}
