package rubery.totoro.digital.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import rubery.totoro.digital.data.repository.ProfileRepository
import rubery.totoro.digital.data.repository.WorkoutSessionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val MAX_DIGITS = 3

class ProfileViewModel(
    private val profileRepository: ProfileRepository,
    private val workoutSessionRepository: WorkoutSessionRepository,
) : ViewModel() {

    private val _weeklyGoalText = MutableStateFlow("")
    val weeklyGoalText: StateFlow<String> = _weeklyGoalText.asStateFlow()

    private val _heightText = MutableStateFlow("")
    val heightText: StateFlow<String> = _heightText.asStateFlow()

    private val _weightText = MutableStateFlow("")
    val weightText: StateFlow<String> = _weightText.asStateFlow()

    init {
        viewModelScope.launch {
            profileRepository.observeWeeklyGoal().collect { value ->
                _weeklyGoalText.value = value.toString()
            }
        }

        viewModelScope.launch {
            profileRepository.observeHeightCm().collect { value ->
                _heightText.value = value?.toString().orEmpty()
            }
        }

        viewModelScope.launch {
            profileRepository.observeWeightKg().collect { value ->
                _weightText.value = value?.toString().orEmpty()
            }
        }
    }

    fun onWeeklyGoalChanged(text: String) {
        if (!isValidDigitsInput(text)) return
        _weeklyGoalText.value = text
        viewModelScope.launch {
            profileRepository.setWeeklyGoal(text.toIntOrNull())
        }
    }

    fun onHeightChanged(text: String) {
        if (!isValidDigitsInput(text)) return
        _heightText.value = text
        viewModelScope.launch {
            profileRepository.setHeightCm(text.toIntOrNull())
        }
    }

    fun onWeightChanged(text: String) {
        if (!isValidDigitsInput(text)) return
        _weightText.value = text
        viewModelScope.launch {
            profileRepository.setWeightKg(text.toIntOrNull())
        }
    }

    fun onResetDataConfirmed() {
        viewModelScope.launch {
            workoutSessionRepository.deleteAll()
        }
    }

    private fun isValidDigitsInput(text: String): Boolean {
        return text.length <= MAX_DIGITS && text.all { it.isDigit() }
    }
}
