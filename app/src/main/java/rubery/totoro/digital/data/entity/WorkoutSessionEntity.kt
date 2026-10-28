package rubery.totoro.digital.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity("workout_sessions")
data class WorkoutSessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "workout_id") val workoutId: Int,
    @ColumnInfo(name = "workout_name") val workoutName: String,
    @ColumnInfo(name = "workout_type") val workoutType: String,
    val difficulty: String,
    @ColumnInfo(name = "completed_exercises") val completedExercises: Int,
    @ColumnInfo(name = "total_exercises") val totalExercises: Int,
    @ColumnInfo(name = "is_fully_completed") val isFullyCompleted: Boolean,
    val timestamp: LocalDateTime,
)
