package rubery.totoro.digital.ui.composable.screen.home.filter.shell

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import rubery.totoro.digital.R
import rubery.totoro.digital.ui.composable.screen.home.filter.FilterChipItem
import rubery.totoro.digital.ui.state.WorkoutFilterMode
import rubery.totoro.digital.ui.theme.LocalDimens

@Composable
fun WorkoutFilterRowShell(
    filterMode: WorkoutFilterMode,
    onFilterModeToggled: () -> Unit,
    items: List<FilterChipItem>,
    modifier: Modifier = Modifier,
    content: @Composable (FilterChipItem) -> Unit,
) {
    val dimens = LocalDimens.current

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = dimens.screenPadding),
        horizontalArrangement = Arrangement.spacedBy(dimens.itemSpacing),
    ) {
        item {
            val isDifficultyMode = filterMode == WorkoutFilterMode.DIFFICULTY
            val rotation by animateFloatAsState(
                targetValue = if (isDifficultyMode) 180f else 0f,
                animationSpec = tween(durationMillis = 250),
                label = "filterModeRotation",
            )

            FilledIconToggleButton(
                checked = isDifficultyMode,
                onCheckedChange = { onFilterModeToggled() },
                shape = RoundedCornerShape(dimens.controlCornerRadius),
                colors = IconButtonDefaults.filledIconToggleButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    checkedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    checkedContentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                ),
            ) {
                Icon(
                    imageVector = Icons.Filled.SwapHoriz,
                    modifier = Modifier.rotate(rotation),
                    contentDescription = stringResource(
                        if (isDifficultyMode) {
                            R.string.workout_filter_mode_difficulty
                        } else {
                            R.string.workout_filter_mode_type
                        }
                    ),
                )
            }
        }

        items(items, key = { item -> item.label }) { item ->
            Box(modifier = Modifier.animateItem()) { content(item) }
        }
    }
}
