package rubery.totoro.digital.ui.composable.screen.onboarding.controls

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import rubery.totoro.digital.ui.composable.screen.onboarding.controls.button.OnboardingButton
import rubery.totoro.digital.ui.composable.screen.onboarding.controls.indicator.OnboardingIndicator
import rubery.totoro.digital.ui.composable.screen.onboarding.controls.label.onboardingButtonLabel
import rubery.totoro.digital.ui.composable.screen.onboarding.controls.shell.OnboardingControlsShell
import rubery.totoro.digital.ui.composable.screen.shared.pulse.actionPulse
import rubery.totoro.digital.ui.theme.LocalDimens

@Composable
fun OnboardingControls(
    pageCount: Int,
    currentPage: Int,
    isLastPage: Boolean,
    onSkipClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val dimens = LocalDimens.current

    OnboardingControlsShell(
        pageCount = pageCount,
        currentPage = currentPage,
        isLastPage = isLastPage,
        onSkipClick = onSkipClick,
        onNextClick = onNextClick,
        modifier = modifier,
        indicator = { pc, cp, indicatorModifier -> OnboardingIndicator(pc, cp, indicatorModifier) },
        buttonLabel = { last, cp, pc -> onboardingButtonLabel(last, cp, pc) },
        button = { label, onClick, buttonModifier ->
            OnboardingButton(
                label,
                onClick,
                buttonModifier.actionPulse(dimens.controlCornerRadius, enabled = isLastPage),
            )
        },
    )
}
