package rubery.totoro.digital.ui.composable.screen.onboarding.motion

data class MotionTiming(
    val durationMs: Int,
    val intensity: Float,
    val staggerMs: Int,
) {
    val isOff: Boolean get() = intensity <= 0f
}
