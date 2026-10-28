package rubery.totoro.digital.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import rubery.totoro.digital.data.entity.WorkoutSessionEntity
import rubery.totoro.digital.data.repository.WorkoutSessionRepository
import rubery.totoro.digital.ui.state.DataUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.time.LocalDate

class HistoryViewModel(
    private val workoutSessionRepository: WorkoutSessionRepository,
) : ViewModel() {
    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate>
        get() = _selectedDate.asStateFlow()

    val sessionsState: StateFlow<DataUiState<List<WorkoutSessionEntity>>> =
        workoutSessionRepository.observeAll()
            .map { sessions -> DataUiState.from(sessions) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = DataUiState.Initial,
            )

    fun onDateSelected(date: LocalDate) {
        _selectedDate.update { date }
    }
}
