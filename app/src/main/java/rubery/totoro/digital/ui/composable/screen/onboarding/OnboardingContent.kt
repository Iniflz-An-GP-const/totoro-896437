package rubery.totoro.digital.ui.composable.screen.onboarding

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.Timer
import androidx.compose.ui.graphics.vector.ImageVector
import rubery.totoro.digital.R

data class OnboardingContent(
    @field:StringRes val titleRes: Int,
    @field:StringRes val descriptionRes: Int,
    val icons: List<ImageVector>,
)

val onboardingPagesContent = listOf(
    OnboardingContent(
        titleRes = R.string.rroil_page_expertise_title,
        descriptionRes = R.string.rroil_page_expertise_description,
        icons = listOf(Icons.Filled.FitnessCenter, Icons.Filled.Insights),
    ),
    OnboardingContent(
        titleRes = R.string.rroil_page_reliability_title,
        descriptionRes = R.string.rroil_page_reliability_description,
        icons = listOf(
            Icons.AutoMirrored.Filled.DirectionsRun,
            Icons.Filled.Timer,
            Icons.Filled.MonitorHeart
        ),
    ),
    OnboardingContent(
        titleRes = R.string.rroil_page_guarantee_title,
        descriptionRes = R.string.rroil_page_guarantee_description,
        icons = listOf(
            Icons.Filled.EmojiEvents,
            Icons.AutoMirrored.Filled.TrendingUp,
            Icons.Filled.CheckCircle
        ),
    ),
)
