package rubery.totoro.digital.ui.composable.screen.home.tips

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import rubery.totoro.digital.data.model.TipModel
import rubery.totoro.digital.ui.composable.screen.home.tips.content.TipCardContent
import rubery.totoro.digital.ui.composable.screen.home.tips.shell.TipCardShell
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

private const val AUTO_ADVANCE_DELAY_MS = 5_000L

@Composable
fun TipCard(
    tips: List<TipModel>,
    modifier: Modifier = Modifier,
) {
    if (tips.isEmpty()) return

    var currentIndex by remember(tips) { mutableIntStateOf(0) }

    LaunchedEffect(tips, currentIndex) {
        delay(AUTO_ADVANCE_DELAY_MS.milliseconds)
        currentIndex = (currentIndex + 1) % tips.size
    }

    TipCardShell(
        tip = tips[currentIndex],
        onAdvance = { currentIndex = (currentIndex + 1) % tips.size },
        modifier = modifier,
    ) { tip ->
        TipCardContent(tip = tip)
    }
}
