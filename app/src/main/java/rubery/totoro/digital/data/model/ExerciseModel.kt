package rubery.totoro.digital.data.model

import androidx.annotation.StringRes
import rubery.totoro.digital.R

/**
 * [texts] maps a two-letter language code ("en", "de", ...) to the exercise
 * name. The "en" entry is mandatory: it is the fallback.
 */
data class ExerciseModel(
    val texts: Map<String, String>,
    val duration: Long?,
    val sets: Int?,
    val repetitions: Int?,
    val restDuration: Long?,
    val intensity: Intensity,
) {
    val name: String get() = texts.forCurrentLocale()
}

enum class Intensity(@field:StringRes val titleRes: Int) {
    LOW(R.string.intensity_low),
    MODERATE(R.string.intensity_moderate),
    HIGH(R.string.intensity_high),
    MAXIMUM(R.string.intensity_maximum)
}