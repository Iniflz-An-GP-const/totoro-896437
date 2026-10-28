package rubery.totoro.digital.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import rubery.totoro.digital.data.entity.WorkoutSessionEntity
import rubery.totoro.digital.data.repository.WorkoutSessionRepository
import rubery.totoro.digital.ui.state.DataUiState
import rubery.totoro.digital.ui.state.WorkoutFilterMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class StatisticsViewModel(
    private val workoutSessionRepository: WorkoutSessionRepository,
) : ViewModel() {
    private val _chartMode = MutableStateFlow(WorkoutFilterMode.TYPE)
    val chartMode: StateFlow<WorkoutFilterMode>
        get() = _chartMode.asStateFlow()

    val sessionsState: StateFlow<DataUiState<List<WorkoutSessionEntity>>> =
        workoutSessionRepository.observeAll()
            .map { sessions -> DataUiState.from(sessions) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = DataUiState.Initial,
            )

    fun onChartModeToggled() {
        _chartMode.update { mode ->
            if (mode == WorkoutFilterMode.TYPE) WorkoutFilterMode.DIFFICULTY else WorkoutFilterMode.TYPE
        }
    }
}
