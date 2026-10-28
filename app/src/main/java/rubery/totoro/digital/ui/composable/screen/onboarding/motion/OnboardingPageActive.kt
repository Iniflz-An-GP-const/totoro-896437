package rubery.totoro.digital.ui.composable.screen.onboarding.motion

import androidx.compose.runtime.compositionLocalOf

/**
 * Whether the page being composed is the one the user is looking at. The pager keeps the
 * neighbouring pages composed, and an idle motion running on them is wasted work; motions
 * stand still while this is false and start from rest when it turns true.
 */
val LocalOnboardingPageActive = compositionLocalOf { true }

/**
 * Whether the page being composed is the current one: the pager flips it halfway through a
 * swipe, earlier than [LocalOnboardingPageActive], which waits until the pager stops.
 * Entrances are triggered by this, so the text starts to appear while the page is still settling.
 */
val LocalOnboardingPageCurrent = compositionLocalOf { true }
