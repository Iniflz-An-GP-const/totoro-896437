package rubery.totoro.digital.ui.composable.screen.home.filter.content

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import rubery.totoro.digital.data.model.Difficulty
import rubery.totoro.digital.ui.composable.screen.home.filter.FilterChipItem
import rubery.totoro.digital.ui.composable.screen.home.filter.selectionPop
import rubery.totoro.digital.ui.composable.screen.shared.modifier.press.PressStrength
import rubery.totoro.digital.ui.composable.screen.shared.modifier.press.pressFeedback
import rubery.totoro.digital.ui.theme.LocalDimens

private val LevelBarWidth = 4.dp
private val LevelBarHeight = 12.dp
private val LevelBarSpacing = 2.dp
private const val LevelBarUnfilledAlpha = 0.3f

@Composable
fun FilterChipContent(item: FilterChipItem, modifier: Modifier = Modifier) {
    val dimens = LocalDimens.current

    val interactionSource = remember { MutableInteractionSource() }

    FilterChip(
        selected = item.selected,
        onClick = item.onClick,
        interactionSource = interactionSource,
        label = { Text(item.label) },
        leadingIcon = when {
            item.icon != null -> {
                {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = dimens.tightSpacing)
                            .size(FilterChipDefaults.IconSize),
                    )
                }
            }

            item.level != null -> {
                {
                    DifficultyLevelBars(
                        level = item.level,
                        modifier = Modifier.padding(start = dimens.tightSpacing)
                    )
                }
            }

            else -> null
        },
        shape = RoundedCornerShape(dimens.controlCornerRadius),
        modifier = modifier
            .pressFeedback(interactionSource, PressStrength.Button)
            .selectionPop(item.selected),
    )
}

@Composable
private fun DifficultyLevelBars(
    level: Int,
    modifier: Modifier = Modifier,
    maxLevel: Int = Difficulty.entries.size,
) {
    val color = LocalContentColor.current

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(LevelBarSpacing),
    ) {
        repeat(maxLevel) { index ->
            Box(
                modifier = Modifier
                    .size(width = LevelBarWidth, height = LevelBarHeight)
                    .background(
                        color = if (index < level) color else color.copy(alpha = LevelBarUnfilledAlpha),
                        shape = RoundedCornerShape(LevelBarWidth / 2),
                    ),
            )
        }
    }
}
