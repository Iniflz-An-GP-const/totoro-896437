package rubery.totoro.digital.data.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.SportsGymnastics
import androidx.compose.ui.graphics.vector.ImageVector
import rubery.totoro.digital.R

/**
 * [texts] maps a two-letter language code ("en", "de", ...) to the workout name.
 * The "en" entry is mandatory: it is the fallback and the source of the name
 * used to look up the photo.
 */
data class WorkoutModel(
    val id: Int,
    val texts: Map<String, String>,
    val exercises: List<ExerciseModel>,
    val difficulty: Difficulty,
    val type: WorkoutType,
    val imageUrl: String,
) {
    val name: String get() = texts.forCurrentLocale()
}

enum class Difficulty(@field:StringRes val titleRes: Int, val level: Int) {
    BEGINNER(R.string.difficulty_beginner, level = 1),
    INTERMEDIATE(R.string.difficulty_intermediate, level = 2),
    ADVANCED(R.string.difficulty_advanced, level = 3),
}

enum class WorkoutType(@field:StringRes val titleRes: Int, val icon: ImageVector) {
    STRENGTH(R.string.workout_type_strength_title, Icons.Filled.FitnessCenter),
    CORE(R.string.workout_type_core_title, Icons.Filled.SelfImprovement),
    UPPER_BODY(R.string.workout_type_upper_body_title, Icons.Filled.SportsGymnastics),
    HIIT(R.string.workout_type_hiit_title, Icons.Filled.Bolt),
}