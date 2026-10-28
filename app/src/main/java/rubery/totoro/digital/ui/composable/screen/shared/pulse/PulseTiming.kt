package rubery.totoro.digital.ui.composable.screen.shared.pulse


data class PulseTiming(
    val durationMs: Int,
    val pauseMs: Int,
    val intensity: Float,
) {
    val isOff: Boolean get() = intensity <= 0f
    val cycleMs: Int get() = durationMs + pauseMs
}
