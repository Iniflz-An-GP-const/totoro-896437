package rubery.totoro.digital.ui.composable.screen.home.greeting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import rubery.totoro.digital.ui.theme.LocalDimens

private val AccentWidth = 48.dp
private val AccentHeight = 5.dp

@Composable
fun HomeGreeting(
    title: String,
    text: String,
    modifier: Modifier = Modifier,
) {
    val dimens = LocalDimens.current

    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .width(AccentWidth)
                .height(AccentHeight)
                .clip(RoundedCornerShape(AccentHeight))
                .background(MaterialTheme.colorScheme.primary),
        )

        Spacer(modifier = Modifier.height(dimens.itemSpacing))

        Text(
            text = title,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineLarge,
        )

        Spacer(modifier = Modifier.height(dimens.tightSpacing))

        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}
