package rubery.totoro.digital.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import rubery.totoro.digital.data.entity.WorkoutSessionEntity
import rubery.totoro.digital.data.model.ExerciseModel
import rubery.totoro.digital.data.repository.WorkoutRepository
import rubery.totoro.digital.data.repository.WorkoutSessionRepository
import rubery.totoro.digital.ui.state.DataUiState
import rubery.totoro.digital.ui.state.ExerciseItemUiState
import rubery.totoro.digital.ui.state.ExercisePhase
import rubery.totoro.digital.ui.state.ExerciseStatus
import rubery.totoro.digital.ui.state.WorkoutUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import kotlin.time.Duration.Companion.milliseconds

class WorkoutViewModel(
    private val workoutId: Int,
    private val workoutRepository: WorkoutRepository,
    private val workoutSessionRepository: WorkoutSessionRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<DataUiState<WorkoutUiState>>(DataUiState.Initial)
    val uiState: StateFlow<DataUiState<WorkoutUiState>>
        get() = _uiState.asStateFlow()

    private var timerJob: Job? = null

    init {
        viewModelScope.launch {
            workoutRepository.observeById(workoutId).collect { workout ->
                _uiState.update {
                    if (workout == null) {
                        DataUiState.Empty
                    } else {
                        DataUiState.Populated(
                            WorkoutUiState(
                                workout = workout,
                                exercises = workout.exercises.map { exercise -> exercise.toItemUiState() },
                                hasStarted = false,
                                isFinished = false,
                            )
                        )
                    }
                }
            }
        }
    }

    fun onStartWorkoutClick() {
        val state = currentWorkout() ?: return
        if (state.hasStarted) return

        updateWorkout { it.copy(hasStarted = true) }
        activateExercise(index = 0)
    }

    fun onAdvanceConfirmed(index: Int) {
        val item = currentWorkout()?.exercises?.getOrNull(index) ?: return
        if (item.status != ExerciseStatus.ACTIVE) return

        timerJob?.cancel()
        advance(index)
    }

    fun onEndWorkoutClick() {
        val state = currentWorkout() ?: return
        if (!state.hasStarted || state.isFinished) return

        timerJob?.cancel()
        updateWorkout { workoutState ->
            workoutState.copy(
                isFinished = true,
                exercises = workoutState.exercises.map { item ->
                    if (item.status == ExerciseStatus.ACTIVE) item.copy(status = ExerciseStatus.PENDING) else item
                },
            )
        }
        saveWorkoutSession()
    }

    private fun currentWorkout(): WorkoutUiState? {
        return (_uiState.value as? DataUiState.Populated)?.data
    }

    private fun updateWorkout(transform: (WorkoutUiState) -> WorkoutUiState) {
        _uiState.update { state ->
            if (state is DataUiState.Populated) DataUiState.Populated(transform(state.data)) else state
        }
    }

    private fun activateExercise(index: Int) {
        val exercises = currentWorkout()?.exercises ?: return
        if (index >= exercises.size) {
            updateWorkout { it.copy(isFinished = true) }
            saveWorkoutSession()
            return
        }

        startPhase(index = index, setNumber = 1, phase = ExercisePhase.WORK)
    }

    private fun saveWorkoutSession() {
        val state = currentWorkout() ?: return
        val completedExercises = state.exercises.count { it.status == ExerciseStatus.COMPLETED }

        viewModelScope.launch {
            workoutSessionRepository.save(
                WorkoutSessionEntity(
                    workoutId = state.workout.id,
                    workoutName = state.workout.name,
                    workoutType = state.workout.type.name,
                    difficulty = state.workout.difficulty.name,
                    completedExercises = completedExercises,
                    totalExercises = state.exercises.size,
                    isFullyCompleted = completedExercises == state.exercises.size,
                    timestamp = LocalDateTime.now(),
                )
            )
        }
    }

    private fun startPhase(index: Int, setNumber: Int, phase: ExercisePhase) {
        timerJob?.cancel()

        val exercise = currentWorkout()?.exercises?.getOrNull(index)?.exercise ?: return
        val totalMs = when (phase) {
            ExercisePhase.WORK -> exercise.duration ?: 0L
            ExercisePhase.REST -> exercise.restDuration ?: 0L
        }
        val isTimed = totalMs > 0L

        updateWorkout { state ->
            state.copy(
                exercises = state.exercises.mapIndexed { i, item ->
                    if (i == index) {
                        item.copy(
                            status = ExerciseStatus.ACTIVE,
                            currentSet = setNumber,
                            phase = phase,
                            isTimed = isTimed,
                            totalMs = totalMs,
                            remainingMs = totalMs,
                        )
                    } else {
                        item
                    }
                },
            )
        }

        if (!isTimed) return

        timerJob = viewModelScope.launch {
            while (true) {
                delay(TIMER_TICK_MS.milliseconds)

                val current = currentWorkout()?.exercises?.getOrNull(index) ?: return@launch
                val newRemaining = (current.remainingMs - TIMER_TICK_MS).coerceAtLeast(0L)

                updateWorkout { state ->
                    state.copy(
                        exercises = state.exercises.mapIndexed { i, item ->
                            if (i == index) item.copy(remainingMs = newRemaining) else item
                        },
                    )
                }

                if (newRemaining <= 0L) {
                    advance(index)
                    break
                }
            }
        }
    }

    private fun advance(index: Int) {
        val item = currentWorkout()?.exercises?.getOrNull(index) ?: return

        when (item.phase) {
            ExercisePhase.WORK -> startPhase(
                index = index,
                setNumber = item.currentSet,
                phase = ExercisePhase.REST
            )

            ExercisePhase.REST -> {
                if (item.currentSet >= item.totalSets) {
                    completeExercise(index)
                } else {
                    startPhase(
                        index = index,
                        setNumber = item.currentSet + 1,
                        phase = ExercisePhase.WORK
                    )
                }
            }
        }
    }

    private fun completeExercise(index: Int) {
        updateWorkout { state ->
            state.copy(
                exercises = state.exercises.mapIndexed { i, item ->
                    if (i == index) item.copy(
                        status = ExerciseStatus.COMPLETED,
                        remainingMs = 0L
                    ) else item
                },
            )
        }
        activateExercise(index + 1)
    }

    private fun ExerciseModel.toItemUiState(): ExerciseItemUiState {
        return ExerciseItemUiState(
            exercise = this,
            status = ExerciseStatus.PENDING,
            totalSets = sets ?: 1,
            currentSet = 1,
            phase = ExercisePhase.WORK,
            isTimed = duration != null,
            totalMs = duration ?: 0L,
            remainingMs = duration ?: 0L,
        )
    }

    companion object {
        private const val TIMER_TICK_MS = 200L
    }
}
