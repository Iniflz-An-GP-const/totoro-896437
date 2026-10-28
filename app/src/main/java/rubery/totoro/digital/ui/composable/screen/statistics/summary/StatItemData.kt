package rubery.totoro.digital.ui.composable.screen.statistics.summary

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class StatItemData(
    val value: String,
    val label: String,
    val icon: ImageVector,
    val iconContainerColor: Color,
    val iconColor: Color,
)
