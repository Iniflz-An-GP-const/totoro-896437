package rubery.totoro.digital.ui.composable.screen.shared.modifier.press

data class PressTempo(
    val pressMs: Int,
    val dampingRatio: Float,
    val stiffness: Float,
)
