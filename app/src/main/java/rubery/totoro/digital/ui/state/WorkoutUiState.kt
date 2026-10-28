package rubery.totoro.digital.ui.state

import rubery.totoro.digital.data.model.ExerciseModel
import rubery.totoro.digital.data.model.WorkoutModel

enum class ExercisePhase {
    WORK,
    REST,
}

enum class ExerciseStatus {
    PENDING,
    ACTIVE,
    COMPLETED,
}

data class ExerciseItemUiState(
    val exercise: ExerciseModel,
    val status: ExerciseStatus,
    val totalSets: Int,
    val currentSet: Int,
    val phase: ExercisePhase,
    /** False for an untimed work set (reps with no explicit duration) — advanced by tap only. */
    val isTimed: Boolean,
    val totalMs: Long,
    val remainingMs: Long,
)

data class WorkoutUiState(
    val workout: WorkoutModel,
    val exercises: List<ExerciseItemUiState>,
    val hasStarted: Boolean,
    val isFinished: Boolean,
)
