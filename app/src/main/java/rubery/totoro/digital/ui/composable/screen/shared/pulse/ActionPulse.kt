package rubery.totoro.digital.ui.composable.screen.shared.pulse

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import rubery.totoro.digital.ui.composable.screen.shared.pulse.timing.ActionPulseTiming
import rubery.totoro.digital.ui.composable.screen.shared.pulse.pulseEffect

@Composable
fun Modifier.actionPulse(cornerRadius: Dp, enabled: Boolean = true): Modifier =
    if (enabled) pulseEffect(cornerRadius, ActionPulseTiming) else this
