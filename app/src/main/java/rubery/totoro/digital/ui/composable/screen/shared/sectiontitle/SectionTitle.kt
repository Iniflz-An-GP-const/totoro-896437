package rubery.totoro.digital.ui.composable.screen.shared.sectiontitle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import rubery.totoro.digital.ui.theme.LocalDimens

private val DashWidth = 20.dp
private val DashHeight = 3.dp

@Composable
fun SectionTitle(
    text: String,
    modifier: Modifier = Modifier,
) {
    val dimens = LocalDimens.current

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .width(DashWidth)
                .height(DashHeight)
                .clip(RoundedCornerShape(DashHeight / 2))
                .background(MaterialTheme.colorScheme.primary),
        )

        Spacer(modifier = Modifier.width(dimens.itemSpacing))

        Text(
            text = text,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}
