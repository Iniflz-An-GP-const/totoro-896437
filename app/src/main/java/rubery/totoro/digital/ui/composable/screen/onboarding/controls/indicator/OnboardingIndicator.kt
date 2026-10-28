package rubery.totoro.digital.ui.composable.screen.onboarding.controls.indicator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import rubery.totoro.digital.ui.theme.LocalDimens

private val IndicatorHeight = 8.dp
private val IndicatorWidthSelected = 24.dp
private val IndicatorWidthUnselected = 8.dp

@Composable
fun OnboardingIndicator(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier,
) {
    val dimens = LocalDimens.current

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimens.itemSpacing),
    ) {
        repeat(pageCount) { page ->
            val isSelected = currentPage == page

            Box(
                modifier = Modifier
                    .height(IndicatorHeight)
                    .width(if (isSelected) IndicatorWidthSelected else IndicatorWidthUnselected)
                    .clip(RoundedCornerShape(50))
                    .background(
                        color = if (isSelected) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.outlineVariant
                        }
                    )
            )
        }
    }
}
