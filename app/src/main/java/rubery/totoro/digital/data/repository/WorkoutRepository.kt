package rubery.totoro.digital.data.repository

import rubery.totoro.digital.data.model.Difficulty
import rubery.totoro.digital.data.model.ExerciseModel
import rubery.totoro.digital.data.model.Intensity
import rubery.totoro.digital.data.model.WorkoutModel
import rubery.totoro.digital.data.model.WorkoutType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class WorkoutRepository {
    private val workouts = listOf<WorkoutModel>(

    )

    fun observeAll(): Flow<List<WorkoutModel>> {
        return flowOf(workouts)
    }

    fun observeById(id: Int): Flow<WorkoutModel?> {
        val service = workouts.firstOrNull { service -> service.id == id }
        return flowOf(service)
    }

    fun getById(id: Int): WorkoutModel? {
        return workouts.firstOrNull { service -> service.id == id }
    }
}
