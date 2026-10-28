package rubery.totoro.digital.ui.composable.screen.workout.completed

import androidx.compose.ui.graphics.vector.ImageVector

data class WorkoutSummary(
    val workoutName: String,
    val typeLabel: String,
    val typeIcon: ImageVector,
    val typeOrdinal: Int,
    val difficultyLabel: String,
    val difficultyLevel: Int,
    val exerciseCount: Int,
    val setCount: Int,
)
