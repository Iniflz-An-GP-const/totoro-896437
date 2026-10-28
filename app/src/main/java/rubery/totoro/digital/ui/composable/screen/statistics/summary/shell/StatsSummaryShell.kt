package rubery.totoro.digital.ui.composable.screen.statistics.summary.shell

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import rubery.totoro.digital.ui.composable.screen.statistics.summary.StatItemData
import rubery.totoro.digital.ui.theme.LocalDimens

@Composable
fun StatsSummaryShell(
    items: List<StatItemData>,
    modifier: Modifier = Modifier,
    content: @Composable (StatItemData) -> Unit,
) {
    val dimens = LocalDimens.current

    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = RoundedCornerShape(dimens.cardCornerRadius),
            )
            .padding(dimens.cardPadding),
        verticalArrangement = Arrangement.spacedBy(dimens.contentSpacing),
    ) {
        items.forEach { item -> content(item) }
    }
}
