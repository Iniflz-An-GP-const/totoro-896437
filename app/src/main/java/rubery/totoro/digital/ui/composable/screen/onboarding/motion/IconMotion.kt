package rubery.totoro.digital.ui.composable.screen.onboarding.motion

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import rubery.totoro.digital.ui.composable.screen.onboarding.motion.timing.IconMotionTiming
import rubery.totoro.digital.ui.composable.screen.onboarding.motion.iconMotionEffect

@Composable
fun Modifier.iconMotion(index: Int): Modifier =
    if (LocalOnboardingPageActive.current) iconMotionEffect(index, IconMotionTiming) else this
