package rubery.totoro.digital.ui.composable.screen.home.filter

import androidx.compose.ui.graphics.vector.ImageVector

data class FilterChipItem(
    val label: String,
    val selected: Boolean,
    val onClick: () -> Unit,
    val icon: ImageVector? = null,
    val level: Int? = null,
)
