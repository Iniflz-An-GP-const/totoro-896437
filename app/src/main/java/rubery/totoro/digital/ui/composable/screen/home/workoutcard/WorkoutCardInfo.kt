package rubery.totoro.digital.ui.composable.screen.home.workoutcard

import androidx.compose.ui.graphics.vector.ImageVector

data class WorkoutCardInfo(
    val name: String,
    val typeLabel: String,
    val typeIcon: ImageVector,
    val difficultyLabel: String,
    val difficultyLevel: Int,
    val exerciseCount: Int,
    /** Position of the category in `WorkoutType`; the card marks it with the category color. */
    val typeOrdinal: Int = 0,
)
